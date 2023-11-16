package com.lbg.serdes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lbg.model.Event;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class EventJsonDeserializer<T> extends JsonDeserializer<Event<T>> {
    public EventJsonDeserializer(TypeReference<Event<T>> typeReference) {
        super(typeReference);
        this.setUseTypeHeaders(false);
    }
}
