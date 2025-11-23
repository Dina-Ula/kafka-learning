package com.lbg.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.lbg.model.EventData;

import java.io.IOException;

public class EventDeserializer extends JsonDeserializer<EventData<?>> implements ContextualDeserializer {

    private JavaType type;

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        this.type = property.getType().containedType(0);
        return this;
    }

    @Override
    public EventData<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        EventData<?> eventData = new EventData<>();
        eventData.setData(deserializationContext.readValue(jsonParser, type));
        return eventData;
    }
}
