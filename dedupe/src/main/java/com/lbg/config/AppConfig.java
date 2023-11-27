package com.lbg.config;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.SettlementCycle;
import com.lbg.service.DeDupeConsumerV1;
import com.lbg.service.DeDupeConsumerV2;
import com.lbg.util.LBGInteractiveQueryService;
import com.lbg.util.SettlementCycleCalculator;
import com.lbg.util.SettlementCycleDataKTables;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;

import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Configuration
public class AppConfig {

    private static final String STORE_NAME = "settlement-cycle-dedup-store";

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @Bean
    public StreamsBuilderFactoryBeanConfigurer kafkaStreamsCustomizer() {
        return factoryBean -> factoryBean.setKafkaStreamsCustomizer(kafkaStreams -> kafkaStreams.setUncaughtExceptionHandler(
                exception -> {
                    System.out.println("I'm called!");
                    return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.REPLACE_THREAD;
                }
        ));
    }

    @Bean
    public LBGInteractiveQueryService getLBGInteractiveQueryService() {
        return new LBGInteractiveQueryService(interactiveQueryService);
    }

    @Bean
    public SettlementCycleCalculator settlementCycleCalculator(InteractiveQueryService interactiveQueryService) {
        return new SettlementCycleCalculator(new SettlementCycleDataKTables(getLBGInteractiveQueryService()));
    }

    @Bean
    public Function<KStream<byte[], Event<FPSPayment>>, KStream<String, Event<SettlementCycle>>> deDupeProducerV1() {
        return (payments) -> payments
                .peek((k, v) -> System.out.println("Event consumed: " + v))
                .selectKey((k, v) -> "SETTLEMENT_CYCLE")
                .mapValues((k, v) -> Event.of(new SettlementCycle(v.getData().getSettlementDate(), v.getData().getSettlementCycle())))
                .peek((k, v) -> System.out.println("Event produced: " + v));
    }

    @Bean
    public BiConsumer<KStream<byte[], Event<FPSPayment>>, GlobalKTable<String, Event<SettlementCycle>>> deDupeConsumerV1(SettlementCycleCalculator settlementCycleCalculator) {
        return new DeDupeConsumerV1(settlementCycleCalculator);
    }

    @Bean
    public StoreBuilder<WindowStore<String, String>> settlementCycleDeDupeStore() {
        return Stores.windowStoreBuilder(Stores.inMemoryWindowStore(STORE_NAME, Duration.ofHours(12), Duration.ofHours(6), false), Serdes.String(), Serdes.String());
    }

    /*@Bean
    public Function<KStream<byte[], Event<FPSPayment>>, KStream<String, Event<SettlementCycle>>> deDupeProducerV2() {
        return (payments) -> payments
                .peek((k, v) -> System.out.println("Event consumed: " + v))
                .transform(() -> new Transformer<byte[], Event<FPSPayment>, KeyValue<String, Event<SettlementCycle>>>() {
                    WindowStore<String, String> windowStore;

                    @Override
                    public void init(final ProcessorContext processorContext) {
                        this.windowStore = processorContext.getStateStore(STORE_NAME);
                    }

                    @Override
                    public KeyValue<String, Event<SettlementCycle>> transform(final byte[] bytes, final Event<FPSPayment> fpsPaymentEvent) {
                        final String settlementDate = fpsPaymentEvent.getData().getSettlementDate();
                        final String settlementCycle = fpsPaymentEvent.getData().getSettlementCycle();

                        final String key = settlementDate + "_" + settlementCycle;

                        try (WindowStoreIterator<String> iterator = windowStore.fetch(key, Long.MIN_VALUE, Long.MAX_VALUE)) {

                            if (iterator.hasNext()) {
                                System.out.println("The settlement cycle exits.");
                                return null;
                            } else {
                                windowStore.put(key, "", Instant.now().toEpochMilli());
                                return new KeyValue<>("SETTLEMENT_CYCLE", Event.of(new SettlementCycle(settlementDate, settlementCycle)));
                            }

                        }
                    }

                    @Override
                    public void close() {
                        if (windowStore != null) {
                            windowStore.close();
                        }
                    }
                }, STORE_NAME)
                .peek((k, v) -> System.out.println("Event produced: " + v));
    }*/

    /*@Bean
    public Function<KStream<byte[], Event<FPSPayment>>, KStream<String, Event<SettlementCycle>>> deDupeProducerV2() {
        return (payments) -> payments
                .peek((k, v) -> System.out.println("Event consumed: " + v))
                .process(() -> new Processor<byte[], Event<FPSPayment>, String, Event<SettlementCycle>>() {
                    ProcessorContext<String, Event<SettlementCycle>> context;
                    WindowStore<String, String> windowStore;

                    @Override
                    public void init(final ProcessorContext<String, Event<SettlementCycle>> context) {
                        this.context = context;
                        this.windowStore = context.getStateStore(STORE_NAME);
                    }

                    @Override
                    public void process(final Record<byte[], Event<FPSPayment>> eventRecord) {
                        final String settlementDate = eventRecord.value().getData().getSettlementDate();
                        final String settlementCycle = eventRecord.value().getData().getSettlementCycle();

                        final String key = settlementDate + "_" + settlementCycle;

                        try (WindowStoreIterator<String> iterator = windowStore.fetch(key, Long.MIN_VALUE, Long.MAX_VALUE)) {

                            if (iterator.hasNext()) {
                                System.out.println("The settlement date/cycle exists.");
                            } else {
                                windowStore.put(key, "", Instant.now().toEpochMilli());
                                context.forward(new Record<>("SETTLEMENT_CYCLE", Event.of(new SettlementCycle(settlementDate, settlementCycle)), Instant.now().toEpochMilli()));
                            }
                        }
                    }

                    @Override
                    public void close() {
                        Processor.super.close();
                    }
                }, STORE_NAME)
                .peek((k, v) -> System.out.println("Event produced: " + v));
    }*/

    @Bean
    public BiConsumer<KStream<byte[], Event<FPSPayment>>, GlobalKTable<String, Event<SettlementCycle>>> deDupeConsumerV2(SettlementCycleCalculator settlementCycleCalculator) {
        return new DeDupeConsumerV2(settlementCycleCalculator);
    }
}
