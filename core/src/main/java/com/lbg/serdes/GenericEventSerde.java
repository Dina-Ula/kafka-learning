package com.lbg.serdes;

import com.lbg.model.Event;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@NoArgsConstructor
public class GenericEventSerde<T> implements Serde<Event<T>> {

    @Override
    public Serializer<Event<T>> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<Event<T>> deserializer() {
        return new JsonDeserializer<>();
    }
}
