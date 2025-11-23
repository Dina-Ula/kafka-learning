package com.lbg.producer;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Properties;
import java.util.UUID;

public class PaymentProducer {
    public static void main(String[] args) {
        Properties config = new Properties();

        //Confluent Cloud
        config.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "ec2-3-10-136-23.eu-west-2.compute.amazonaws.com:9094");
        config.put("security.protocol", "SASL_SSL");
        config.put("sasl.kerberos.service.name", "kafka");
        config.put("sasl.jaas.config", "com.sun.security.auth.module.Krb5LoginModule required useKeyTab=true storeKey=true keyTab='/Users/dina/Learning/Kafka/kafka-security-master/kerberos/writer.user.keytab' principal='writer@KAFKA.SECURE';");

        config.put("ssl.keystore.location", "/Users/dina/Learning/Kafka/kafka-security-master/ssl/kafka.client.keystore.jks");
        config.put("ssl.keystore.password", "clientpass");
        config.put("ssl.truststore.location", "/Users/dina/Learning/Kafka/kafka-security-master/ssl/kafka.client.truststore.jks");
        config.put("ssl.truststore.password", "clientpass");

        //Local
        //properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        config.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        config.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
        config.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        config.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        config.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        config.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");

        config.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.lbg.producer.partitioner.CustomPartitioner");

        Producer<String, Event<FPSPayment>> producer = new KafkaProducer<>(config);

        produceRecordByCustomPartition("0", producer);
        produceRecordByCustomPartition("1", producer);
        produceRecordByCustomPartition("2", producer);
        produceRecordByCustomPartition("3", producer);
        produceRecordByCustomPartition("4", producer);
        produceRecordByCustomPartition("5", producer);

        producer.close();
    }

    private static void produceRecordByCustomPartition(final String key, final Producer<String, Event<FPSPayment>> producer) {
        for (int i = 0; i < 10; i++) {

            final ProducerRecord<String, Event<FPSPayment>> record = new ProducerRecord<>(
                    "fps-payment-offset",
                    key,
                    Event.of(new FPSPayment(UUID.randomUUID().toString(), "third10ForP6", "123456", "1000", "", "")));
            producer.send(record);
        }
    }
}
