package com.lbg.topology;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbg.service.ExternalService;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class KStreamTopology {

    private final ObjectMapper mapper = new ObjectMapper();

    private ExternalService externalService;

    public KStreamTopology(final ExternalService externalService) {
        this.externalService = externalService;
    }

    @Bean
    public Consumer<KStream<String, String>> kafkaStreamConsumer() {
        return input -> input.foreach((key, value) -> {

            System.out.println("Key:" + key);
            System.out.println("Value:" + value);

            try {
                final JsonNode node = mapper.readTree(value);

                final JsonNode data = node.get("data");
                final String appGrp = data.get("appGrp").asText();
                final String accId = data.get("accId").asText();

                System.out.println(appGrp);
                System.out.println(accId);

                final String msg = externalService.post(appGrp, accId);
                System.out.println(msg);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
