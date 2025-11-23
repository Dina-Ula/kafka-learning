package com.lbg.config;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.ReferenceFPSSortCode;
import com.lbg.service.FPSPaymentConsumer;
import com.lbg.service.FPSPaymentConsumerV2;
import com.lbg.util.LBGInteractiveQueryService;
import com.lbg.util.ReferenceDataKTables;
import com.lbg.util.ReferenceDataValidator;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class AppConfig {

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @Bean
    public LBGInteractiveQueryService getLBGInteractiveQueryService() {
        return new LBGInteractiveQueryService(interactiveQueryService);
    }

    @Bean
    public ReferenceDataValidator referenceDataValidator(InteractiveQueryService interactiveQueryService) {
        return new ReferenceDataValidator(new ReferenceDataKTables(getLBGInteractiveQueryService()));
    }

    @Bean
    public BiConsumer<KStream<byte[], Event<FPSPayment>>, GlobalKTable<String, Event<ReferenceFPSSortCode>>> fpsPaymentV2(ReferenceDataValidator referenceDataValidator) {
        return new FPSPaymentConsumerV2(referenceDataValidator);
    }

    /*@Bean
    public Function<GlobalKTable<String, Event<ReferenceFPSSortCode>>,
            Function<GlobalKTable<String, Event<ReferenceFPSSortCode>>,
                    Consumer<KStream<byte[], Event<FPSPayment>>>>> fpsPaymentV2(ReferenceDataValidator referenceDataValidator) {
        //return new FPSPaymentConsumerV3(referenceDataValidator);
        *//*return p -> g1 -> g2 -> {
            p.peek((key, value) -> {
                System.out.println("Event consumed: " + value);
            }).foreach((key, value) -> {
                try {
                    referenceDataValidator.validateReferenceFPSSortCode1(value.getData());
                    referenceDataValidator.validateReferenceFPSSortCode2(value.getData());
                } catch (Exception e) {
                    System.out.println("Exception Handled Event Consumer: " + e);
                }
            });
        };*//*
        return p -> g1 -> g2 -> {
            new FPSPaymentConsumer(referenceDataValidator);
        };
    }*/


    @Bean
    public Function<KStream<byte[], Event<FPSPayment>>,
            Function<GlobalKTable<String, Event<ReferenceFPSSortCode>>,
                    Consumer<GlobalKTable<String, Event<ReferenceFPSSortCode>>>>> fpsPaymentV3(ReferenceDataValidator referenceDataValidator) {
        //return new FPSPaymentConsumerV3(referenceDataValidator);
        /*return p -> g1 -> g2 -> {
            p.peek((key, value) -> {
                System.out.println("Event consumed: " + value);
            }).foreach((key, value) -> {
                try {
                    referenceDataValidator.validateReferenceFPSSortCode1(value.getData());
                    referenceDataValidator.validateReferenceFPSSortCode2(value.getData());
                } catch (Exception e) {
                    System.out.println("Exception Handled Event Consumer: " + e);
                }
            });
        };*/
        return p -> g1 -> g2 -> {
            new FPSPaymentConsumer(referenceDataValidator).accept(p);
        };
    }

    @Bean
    public Consumer<KStream<byte[], Event<FPSPayment>>> fpsPaymentV1(ReferenceDataValidator referenceDataValidator) {
        return new FPSPaymentConsumer(referenceDataValidator);
    }

    @Bean
    public Consumer<GlobalKTable<String, Event<ReferenceFPSSortCode>>> referenceFpsSortCode1() {
        return referenceFPSSortCode1 -> {
            System.out.println(referenceFPSSortCode1.queryableStoreName());
        };
    }

    @Bean
    public Consumer<GlobalKTable<String, Event<ReferenceFPSSortCode>>> referenceFpsSortCode2() {
        return referenceFPSSortCode2 -> {
            System.out.println(referenceFPSSortCode2.queryableStoreName());
        };
    }
}
