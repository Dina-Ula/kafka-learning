package com.lbg.vanilla;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VanillaApp {

    public static void main(String[] args) {
        Properties properties = new Properties();

        // Confluent Local Common configuration
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:29092");

        // Confluent Cloud Common configuration
        /*properties.setProperty("sasl.mechanism", "PLAIN");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-l6wr6.europe-west2.gcp.confluent.cloud:9092");
        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='XPC5QDUCFQ4N5VQW' password='Ji/v7IFe0SBSuWKXWlE4jzVoWWvf20K7TLQpzvJtESWdK/VPP3fMLcgtznBYsq6l';");
        properties.setProperty("security.protocol", "SASL_SSL");*/

        /*properties.setProperty("basic.auth.credentials.source", "USER_INFO");
        properties.setProperty("schema.registry.basic.auth.user.info", "RPJXOK3O3ONHZ64Y:pJds8EAHUSrMXMp/HawHGDMGZtde7XcUbcKaEs1gcWKJDUAY+8zsptc+mE3ErdBO");
        properties.setProperty("schema.registry.url", "https://psrc-4j8q8.europe-west3.gcp.confluent.cloud");*/


        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        //Multi-thread
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(() -> {

            Producer<String, String> producer = new KafkaProducer<>(properties);

            for (int i = 1; i < 1000000; i++) {

                String appGrp = "a";
                int accId = 100000;

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                producer.send(new ProducerRecord<>(
                        "my-topic",
                        String.valueOf(i),
                        "{\"data\":{\"appGrp\":\"" + appGrp + "\",\"accId\":\"" + accId + "\"}}"));
            }

            producer.close();
        });

        executorService.execute(() -> {

            Producer<String, String> producer = new KafkaProducer<>(properties);

            for (int i = 1; i < 1000000; i++) {

                String appGrp = "b";
                int accId = 100001;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                producer.send(new ProducerRecord<>(
                        "my-topic",
                        String.valueOf(i),
                        "{\"data\":{\"appGrp\":\"" + appGrp + "\",\"accId\":\"" + accId + "\"}}"));
            }

            producer.close();
        });

        executorService.execute(() -> {

            Producer<String, String> producer = new KafkaProducer<>(properties);

            for (int i = 1; i < 1000000; i++) {

                String appGrp = "c";
                int accId = 100002;

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                producer.send(new ProducerRecord<>(
                        "my-topic",
                        String.valueOf(i),
                        "{\"data\":{\"appGrp\":\"" + appGrp + "\",\"accId\":\"" + accId + "\"}}"));
            }

            producer.close();
        });

        executorService.shutdown();

        /*for (int i = 100000; i <= 200000; i++) {
            producer.send(new ProducerRecord<>(
                    "reference-fps-sort-codes-1",
                    String.valueOf(i),
                    "{\"data\":{\"sortCode\":\"" + i + "\",\"fpsEnabled\":true}}"));

            System.out.println("No of records produced: " + i);
        }

        for (int i = 100000; i <= 400000; i++) {
            producer.send(new ProducerRecord<>(
                    "reference-fps-sort-codes-2",
                    String.valueOf(i),
                    "{\"data\":{\"sortCode\":\"" + i + "\",\"fpsEnabled\":true}}"));

            System.out.println("No of records produced: " + i);
        }*/

        /*for (int i = 1; i < 2; i++) {
            producer.send(new ProducerRecord<>(
                    "acs.request.transactions.topic",
                    String.valueOf(i),
                    "{\"eventName\":\"acs_request_transactions\", \"data\": {\"data\":" + i + "}}"));

            System.out.println("No of records produced: " + i);
        }*/


        /*for (int i = 1; i < 50000000; i++) {
            producer.send(new ProducerRecord<>(
                    "mq_request_response_transactions-request-state-store-changelog",
                    String.valueOf(i + "-mq"),
                    "{\"eventName\":\"acs_request_transactions\", \"data\": {\"data\":" + i + "}}"));

            System.out.println("No of records produced: " + i);
        }*/

        /*for (int i = 1; i < 2; i++) {
            producer.send(new ProducerRecord<>(
                    "mq.response.transactions.topic",
                    String.valueOf(i + "-mq"),
                    "{\"eventName\":\"mq_response_transactions\", \"data\": {\"data\":" + i + "}}"));

            System.out.println("No of records produced: " + i);
        }*/

        /*for (int i = 1; i < 100000; i++) {
            producer.send(new ProducerRecord<>(
                    "acs.request.transactions.topic",
                    String.valueOf(i),
                    "{\"eventName\":\"acs_request_transactions\", \"data\": {\"data\":" + i + "}}"));

            System.out.println("No of records produced: " + i);

            producer.send(new ProducerRecord<>(
                    "mq.response.transactions.topic",
                    String.valueOf(i + "-mq"),
                    "{\"eventName\":\"mq_response_transactions\", \"data\": {\"data\":" + i + "}}"));

            System.out.println("No of records produced: " + i);
        }*/

        /*for (int i = 1; i < 1000000; i++) {
            for (int j = 1; j < 4; j++) {
                producer.send(new ProducerRecord<>(
                        "camel.kafka.topic",
                        String.valueOf(UUID.randomUUID().toString()),
                        "{\"eventName\":\"Camel Testing\", \"data\": {\"data\":" + i + "}}"));

                System.out.println("No of records produced: " + i);
            }

            Thread.sleep(1000);
        }*/
    }
}
