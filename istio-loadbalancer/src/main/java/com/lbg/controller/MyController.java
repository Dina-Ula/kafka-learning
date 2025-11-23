package com.lbg.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class MyController {

    private static final Logger LOG = LoggerFactory.getLogger(MyController.class);

    //private final Map<String, String> inFlightMessage = new HashMap<>();

    @Autowired
    private KafkaTemplate<String, String> syncKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> asyncKafkaTemplate;

    @PostMapping("/publish_message")
    public ResponseEntity<String> publish_message(@RequestBody Map<String, String> messages) throws ExecutionException, InterruptedException {

        for (Map.Entry<String, String> message : messages.entrySet()) {

            /*if (inFlightMessage.containsKey(key)) {
                if (inFlightMessage.get(key).isBlank()) {
                    return ResponseEntity.status(SERVICE_UNAVAILABLE).build();
                } else {
                    return ResponseEntity.ok("");
                }
            }*/

            //inFlightMessage.put(key, "");


            //Async call
            long startTime = System.nanoTime();
            ProducerRecord<String, String> asyncProducerRecord = new ProducerRecord<>("transactions.in.topic-async", message.getKey(), message.getValue());
            asyncKafkaTemplate.send(asyncProducerRecord);

            //Sync call
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("transactions.in.topic", message.getKey(), message.getValue());
            final ListenableFuture<SendResult<String, String>> future = syncKafkaTemplate.send(producerRecord);
            future.get();
            long endTime = System.nanoTime();

            long duration = (endTime - startTime) / 1000000;
            LOG.info("Time taken to execute it: " + duration);
            if (duration > 400) {
                LOG.info("Time taken is more than 400 ms: " + duration);
            }
        }

        return ResponseEntity.ok("Got the message");
    }
}
