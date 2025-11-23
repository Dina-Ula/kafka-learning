package com.lbg.serdes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lbg.model.MQEvent;

public class MQEventJsonDeserializer extends EventJsonDeserializer<MQEvent> {

    public MQEventJsonDeserializer() {
        super(new TypeReference<>() {
        });
    }
}
