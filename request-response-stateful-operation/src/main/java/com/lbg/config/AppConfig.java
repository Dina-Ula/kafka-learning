package com.lbg.config;


import com.lbg.model.ACSEvent;
import com.lbg.model.Event;
import com.lbg.model.MQEvent;
import com.lbg.model.RoutedEvent;
import com.lbg.serdes.ACSEventSerde;
import com.lbg.serdes.GenericEventSerde;
import com.lbg.serdes.MQEventSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.ProcessorSupplier;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binding.BindingsLifecycleController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Configuration
public class AppConfig {

    @Autowired
    private ApplicationContext applicationContext;

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    private static final String REQUEST_STATE_STORE = "request-state-store";

    private static final String SEQUENCE_STATE_STORE = "sequence-state-store";

    @Bean
    public StoreBuilder<TimestampedKeyValueStore<String, Event<ACSEvent>>> requestStateStore() {
        return Stores
                .timestampedKeyValueStoreBuilder(Stores.persistentTimestampedKeyValueStore(REQUEST_STATE_STORE), Serdes.String(), new ACSEventSerde())
                .withCachingDisabled();
    }

    @Bean
    public StoreBuilder<TimestampedKeyValueStore<String, Long>> sequenceStateStore() {
        return Stores.timestampedKeyValueStoreBuilder(Stores.persistentTimestampedKeyValueStore(SEQUENCE_STATE_STORE), Serdes.String(), Serdes.Long());
    }

    @Bean
    public Function<KStream<String, Bytes>, KStream<String, Bytes>> acs_request_transactions() {
        return transactions -> transactions.peek((k, v) -> log.info("acs_request_transactions: key - {}", k)).map((k, v) -> new KeyValue<>(k + "-mq", v));
    }

    @Bean
    public BiConsumer<KStream<String, Event<ACSEvent>>, KStream<String, Event<MQEvent>>> mq_request_response_transactions() {
        return (requestTransactions, responseTransactions) -> {
            requestTransactions
                    .peek((k, v) -> log.info("request transaction - {}, {}", k, v))
                    .process(getRequestProcessorSupplier(), REQUEST_STATE_STORE, SEQUENCE_STATE_STORE)
                    .peek((k, v) -> System.out.println("Key: " + k))
                    .split()
                    .branch((k, v) -> v.getRoute().equalsIgnoreCase("mq"), Branched.withConsumer(ks -> ks.mapValues(RoutedEvent::getMqEvent).to("mq.request.transactions.topic", Produced.with(Serdes.String(), new MQEventSerde()))))
                    .branch((k, v) -> v.getRoute().equalsIgnoreCase("fpp"), Branched.withConsumer(ks -> ks.mapValues(RoutedEvent::getAcsEvent).to("exception.topic", Produced.with(Serdes.String(), new ACSEventSerde()))))
                    .noDefaultBranch();

            /*requestTransactions
                    .peek((k, v) -> log.info("request transaction - {}, {}", k, v))
                    .filter((k, v) -> true)
                    .to("acs.response.transactions.intermediate.topic", Produced.with(Serdes.String(), new MQEventSerde()));*/

            responseTransactions
                    .peek((k, v) -> log.info("response transaction - {}, {}", k, v))
                    .process(getResponseProcessorSupplier(), REQUEST_STATE_STORE)
                    .to("acs.response.transactions.intermediate.topic", Produced.with(Serdes.String(), new GenericEventSerde()));
        };
    }

    @Bean
    public Function<KStream<String, Event<MQEvent>>, KStream<String, Event<ACSEvent>>> acs_response_transactions() {
        return transactions -> transactions.peek((k, v) -> log.info("acs_response_transactions: {}, {}", k, v)).map((k, v) -> new KeyValue<>(k.replace("-mq", ""), new Event<>("acs_response_transactions", new ACSEvent(v.getData().getSourceData()))));
    }

    private ProcessorSupplier<String, Event<ACSEvent>, String, RoutedEvent> getRequestProcessorSupplier() {

        return () -> new Processor<>() {

            private BindingsLifecycleController bindingsLifecycleController;

            private TimestampedKeyValueStore<String, Event<ACSEvent>> requestStateStore;

            private TimestampedKeyValueStore<String, Long> sequenceStateStore;

            private ProcessorContext<String, RoutedEvent> context;

            @Override
            public void init(final ProcessorContext<String, RoutedEvent> context) {
                this.context = context;
                Processor.super.init(context);
                requestStateStore = context.getStateStore(AppConfig.REQUEST_STATE_STORE);
                sequenceStateStore = context.getStateStore(AppConfig.SEQUENCE_STATE_STORE);
                context.schedule(Duration.ofSeconds(5), PunctuationType.WALL_CLOCK_TIME, this::wallClockTimePunctuator);
            }

            private void wallClockTimePunctuator(Long timestamp) {

                try (KeyValueIterator<String, ValueAndTimestamp<Event<ACSEvent>>> iterator = requestStateStore.all()) {

                    final long approximateNumEntries = requestStateStore.approximateNumEntries();

                    System.out.println("Task Id: " + context.taskId());

                    System.out.println("The number of records in the state state is: " + approximateNumEntries);

                    bindingsLifecycleController = applicationContext.getBean(BindingsLifecycleController.class);

                    final List<Binding<?>> bindings = bindingsLifecycleController.queryState("mq_request_response_transactions-in-0");

                    boolean isBindingPaused = false;
                    for (Binding binding : bindings) {
                        if (binding.isPaused()) {
                            isBindingPaused = true;
                        }
                    }

                    if (approximateNumEntries > 20) {
                        if (!isBindingPaused) {
                            System.out.println("The binding is mq_request_response_transactions-in-0 is going to be paused.");
                            bindingsLifecycleController.changeState("mq_request_response_transactions-in-0", BindingsLifecycleController.State.PAUSED);
                        }
                    } else {
                        if (isBindingPaused) {
                            System.out.println("The binding is mq_request_response_transactions-in-0 is going to be resumed.");
                            bindingsLifecycleController.changeState("mq_request_response_transactions-in-0", BindingsLifecycleController.State.RESUMED);
                        }
                    }

                    while (iterator.hasNext()) {
                        final KeyValue<String, ValueAndTimestamp<Event<ACSEvent>>> keyValue = iterator.next();

                        final String eventName = keyValue.value.value().getEventName();
                        final String data = keyValue.value.value().getData().getData();

                        log.info("Key: {}, Value: {}", keyValue.key, data);

                        RoutedEvent event = new RoutedEvent(null, new Event<>(eventName, new MQEvent(data, "")), "mq.request.transactions.topic", "mq");
                        context.forward(new Record<>(keyValue.key, event, Instant.now().toEpochMilli()));
                    }
                }
            }

            @Override
            public void process(final Record<String, Event<ACSEvent>> transactionRecord) {
                log.info("Storing transactionRecord {}", transactionRecord);

                bindingsLifecycleController = applicationContext.getBean(BindingsLifecycleController.class);

                final List<Map<?, ?>> maps = bindingsLifecycleController.queryStates();
                for (Map map : maps) {
                    System.out.println("Map - Keys: " + map.keySet());
                    System.out.println("Map - Values: " + map.values());
                }

                bindingsLifecycleController.changeState("mq_request_response_transactions-in-0", BindingsLifecycleController.State.STOPPED);

                context.recordMetadata().ifPresent(recordMetadata -> {
                    System.out.println("Partition: " + recordMetadata.partition());
                });

                final ValueAndTimestamp<Long> sequenceNumberValueAndTimestamp = sequenceStateStore.get("sequenceNumber");

                Long sequenceNumber;
                if (Objects.isNull(sequenceNumberValueAndTimestamp)) {
                    sequenceNumber = 1L;
                    System.out.println("sequenceNumber initial: " + sequenceNumber);
                } else {
                    sequenceNumber = sequenceStateStore.get("sequenceNumber").value();
                    sequenceNumber = ++sequenceNumber;
                    System.out.println("sequenceNumber after initial: " + sequenceNumber);
                }

                System.out.println("sequenceNumber after increment: " + sequenceNumber);
                sequenceStateStore.put("sequenceNumber", ValueAndTimestamp.make(sequenceNumber, Instant.now().toEpochMilli()));

                final String eventName = transactionRecord.value().getEventName();

                RoutedEvent event;

                if ("1".equalsIgnoreCase(transactionRecord.value().getData().getData())) {
                    event = new RoutedEvent(new Event<>(eventName, transactionRecord.value().getData()), null, "exception.topic", "fpp");
                } else {
                    event = new RoutedEvent(null, new Event<>(eventName, new MQEvent(transactionRecord.value().getData().getData(), "")), "mq.request.transactions.topic", "mq");

                }

                requestStateStore.putIfAbsent(transactionRecord.key(), ValueAndTimestamp.make(transactionRecord.value(), Instant.now().toEpochMilli()));
                context.forward(new Record<>(transactionRecord.key(), event, Instant.now().toEpochMilli()));
            }

            @Override
            public void close() {
                Processor.super.close();
            }
        };
    }

    private ProcessorSupplier<String, Event<MQEvent>, String, Event<?>> getResponseProcessorSupplier() {

        return () -> new Processor<>() {

            private TimestampedKeyValueStore<String, Event<ACSEvent>> store;

            private ProcessorContext<String, Event<?>> context;

            @Override
            public void init(final ProcessorContext<String, Event<?>> context) {
                this.context = context;
                Processor.super.init(context);
                store = context.getStateStore(AppConfig.REQUEST_STATE_STORE);
            }

            @Override
            public void process(final Record<String, Event<MQEvent>> transactionRecord) {

                final ValueAndTimestamp<Event<ACSEvent>> requestTransaction = store.get(transactionRecord.key());

                /*try (final KeyValueIterator<String, ValueAndTimestamp<String>> all = store.all()) {
                    while (all.hasNext()) {
                        final KeyValue<String, ValueAndTimestamp<String>> next = all.next();
                        log.info("All Records: Key - {} | Value - {}", next.key, next.value.value());
                    }
                }*/

                log.info("Found transactionRecord {}", requestTransaction);

                if (!Objects.isNull(requestTransaction) && !Objects.isNull(requestTransaction.value())) {
                    context.forward(new Record<>(transactionRecord.key(), new Event<>("", new MQEvent(requestTransaction.value().getData().getData(), "")), Instant.now().toEpochMilli()));
                    store.delete(transactionRecord.key());
                } else {
                    throw new RuntimeException();
                }
            }

            @Override
            public void close() {
                Processor.super.close();
            }
        };
    }
}
