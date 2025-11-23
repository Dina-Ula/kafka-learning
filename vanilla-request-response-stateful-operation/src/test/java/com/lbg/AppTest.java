package com.lbg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lbg.deserializer.EventDeserializer;
import com.lbg.model.EventData;
import com.lbg.model.EventWithRequestData;
import com.lbg.model.EventWithResponseData;

public class AppTest {

    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(EventData.class, new EventDeserializer());
        mapper.registerModule(module);


        String requestEventJson = "{\"eventName\":\"requestEvent\",\"data\":{\"requestData\":\"This is a request data!\"}}";
        EventWithRequestData requestData = mapper.readValue(requestEventJson, EventWithRequestData.class);
        System.out.println("Request event: " + requestData);

        String responseEventJson = "{\"eventName\":\"responseEvent\",\"data\":{\"responseData\":\"This is a response data!\"}}";
        EventWithResponseData responseData = mapper.readValue(responseEventJson, EventWithResponseData.class);
        System.out.println("Response event: " + responseData);
    }
}
