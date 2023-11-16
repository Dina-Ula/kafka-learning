package com.lbg.serdes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lbg.model.Event;
import com.lbg.model.ReferenceFPSSortCode;

public class SortCodeFPSEventJsonDeserializer extends EventJsonDeserializer<ReferenceFPSSortCode> {

    public SortCodeFPSEventJsonDeserializer() {
        super(new TypeReference<Event<ReferenceFPSSortCode>>() {
        });
    }
}
