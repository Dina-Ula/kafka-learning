package com.lbg.config;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private KafkaTemplate<String, String> kstreamKafkaTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @Bean
    public Consumer<KStream<String, String>> transactions() {
        return transactions -> transactions.peek((k, v) -> LOG.info("transactions: {}", v)).foreach((k, v) -> {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("transactions.out.topic", k, v);
            final ListenableFuture<SendResult<String, String>> future = kstreamKafkaTemplate.send(producerRecord);
            try {
                future.get();
            } catch (InterruptedException e) {
                System.out.println("ERR: " + e);
            } catch (ExecutionException e) {
                System.out.println("ERR: " + e);
            }
        });
    }
}
