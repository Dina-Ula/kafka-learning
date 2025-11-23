package com.lbg.serdes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lbg.model.ACSEvent;
import com.lbg.model.Event;

public class ACSEventJsonDeserializer extends EventJsonDeserializer<ACSEvent> {

    public ACSEventJsonDeserializer() {
        super(new TypeReference<Event<ACSEvent>>() {
        });
    }
}
