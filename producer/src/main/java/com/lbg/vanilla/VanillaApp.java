package com.lbg.vanilla;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class VanillaApp {

    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.setProperty("sasl.mechanism", "PLAIN");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-l6wr6.europe-west2.gcp.confluent.cloud:9092");
        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='RPJXOK3O3ONHZ64Y' password='pJds8EAHUSrMXMp/HawHGDMGZtde7XcUbcKaEs1gcWKJDUAY+8zsptc+mE3ErdBO';");
        properties.setProperty("security.protocol", "SASL_SSL");

        properties.setProperty("basic.auth.credentials.source", "USER_INFO");
        properties.setProperty("schema.registry.basic.auth.user.info", "RPJXOK3O3ONHZ64Y:pJds8EAHUSrMXMp/HawHGDMGZtde7XcUbcKaEs1gcWKJDUAY+8zsptc+mE3ErdBO");
        properties.setProperty("schema.registry.url", "https://psrc-4j8q8.europe-west3.gcp.confluent.cloud");

        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");

        Producer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 100000; i < 300000; i++) {

            producer.send(new ProducerRecord<>(
                    "reference-fps-sort-codes-1",
                    String.valueOf(i),
                    "{\"data\":{\"sortCode\":\"" + i + "\",\"fpsEnabled\":true}}"));

            System.out.println("No of records produced: " + i);
        }

        producer.close();
    }
}
