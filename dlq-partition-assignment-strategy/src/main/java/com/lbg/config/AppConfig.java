package com.lbg.config;

import com.lbg.model.ACSEvent;
import com.lbg.model.Event;
import com.lbg.model.USMEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.ProcessorSupplier;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Configuration
public class AppConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public Function<KStream<String, Event<USMEvent>>, KStream<String, Event<ACSEvent>>[]> topology_1() {
        return requestTransactions -> requestTransactions
                .peek((k, v) -> log.info("topology 1 - {}, {}", k, v))
                .split()
                .branch((k, v) -> true,
                        Branched.withFunction(auditTransactions -> auditTransactions
                                .peek((k, v) -> log.info("audit branch - {}, {}", k, v))))
                .branch((k, v) -> true,
                        Branched.withFunction(statusTransactions -> statusTransactions
                                .peek((k, v) -> log.info("status branch - {}, {}", k, v))))
                .branch((k, v) -> true,
                        Branched.withFunction(transactions -> transactions
                                .peek((k, v) -> log.info("topology 1 - {}, {}", k, v))
                                .process(getProcessorSupplier())
                                .flatMap((k, v) -> {

                                    List<KeyValue<String, Event<USMEvent>>> list = new ArrayList<>();

                                    /*final Set<Map.Entry<String, Event<ACSEvent>>> entries = v.entrySet();

                                    for (Map.Entry<String, Event<ACSEvent>> entry : v.entrySet()) {
                                        list.add(new KeyValue<>(entry.getKey(), entry.getValue()));
                                    }*/

                                    return list;
                                })
                        )
                )
                .noDefaultBranch()
                .values()
                .toArray(new KStream[0]);
    }

    /*.flatMap((k, v) -> {

        List<KeyValue<String, Event<ACSEvent>>> list = new ArrayList<>();
        for (Map.Entry<String, Event<ACSEvent>> entry : v.entrySet()) {
            list.add(new KeyValue<>(entry.getKey(), entry.getValue()));
        }

        return list;
    })*/

    private ProcessorSupplier<String, Event<USMEvent>, String, Map<String, Event<ACSEvent>>> getProcessorSupplier() {

        return () -> new Processor<>() {

            private ProcessorContext<String, Map<String, Event<ACSEvent>>> context;

            @Override
            public void init(final ProcessorContext<String, Map<String, Event<ACSEvent>>> context) {
                this.context = context;
                Processor.super.init(context);
            }

            @Override
            public void process(final Record<String, Event<USMEvent>> transactionRecord) {

                final String eventName = "acs_request_transactions";

                Map<String, Event<ACSEvent>> eventMap = new HashMap<>();
                eventMap.put("acs_request_transactions_1", new Event<>(eventName, new ACSEvent("event_1")));
                eventMap.put("acs_request_transactions_2", new Event<>(eventName, new ACSEvent("event_2")));
                eventMap.put("acs_request_transactions_3", new Event<>(eventName, new ACSEvent("event_3")));
                eventMap.put("acs_request_transactions_4", new Event<>(eventName, new ACSEvent("event_4")));

                Record<String, Map<String, Event<ACSEvent>>> records = new Record<>(Strings.EMPTY, eventMap, Instant.now().toEpochMilli());

                context.forward(records);
                context.commit();
            }

            @Override
            public void close() {
                Processor.super.close();
            }
        };
    }
}
