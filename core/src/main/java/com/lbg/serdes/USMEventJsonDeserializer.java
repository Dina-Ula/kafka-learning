package com.lbg.serdes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lbg.model.Event;
import com.lbg.model.USMEvent;

public class USMEventJsonDeserializer extends EventJsonDeserializer<USMEvent> {

    public USMEventJsonDeserializer() {
        super(new TypeReference<Event<USMEvent>>() {
        });
    }
}
