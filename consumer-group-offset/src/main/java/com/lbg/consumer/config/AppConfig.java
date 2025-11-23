package com.lbg.consumer.config;

import org.apache.kafka.common.errors.TopicAuthorizationException;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.autoconfigure.kafka.StreamsBuilderFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.function.Function;

@Configuration
public class AppConfig {

    @Bean
    public StreamsBuilderFactoryBeanCustomizer customizer() {
        return factoryBean -> factoryBean.setKafkaStreamsCustomizer(streams -> {
            streams.setUncaughtExceptionHandler(new CustomStreamsExceptionHandler());
        });
    }

    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {
        return new DefaultErrorHandler(
                (record, exception) -> {
                    if (exception instanceof TopicAuthorizationException) {
                        System.err.println("Authorization failed for topic: " + record.topic());
                        // Stop retrying for this error
                    }
                },
                new FixedBackOff(1000L, 3) // 3 retries with a 1-second interval
        );
    }

    @Bean
    public Function<KStream<String, Bytes>, KStream<String, Bytes>> fpsPaymentOffset() {
        return payment -> payment.peek((key, value) -> System.out.println("Consumed: " + value)).map((k, v) -> new KeyValue<>(k + "-mq", v));
    }

    /*@Bean
    public Consumer<KStream<byte[], Event<FPSPayment>>> fpsPaymentOffset() {
        return payment -> payment.peek((key, value) -> System.out.println("Consumed: " + value));
    }*/
}
