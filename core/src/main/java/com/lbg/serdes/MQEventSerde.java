package com.lbg.serdes;

import com.lbg.model.Event;
import com.lbg.model.MQEvent;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@NoArgsConstructor
public class MQEventSerde implements Serde<Event<MQEvent>> {

    @Override
    public Serializer<Event<MQEvent>> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<Event<MQEvent>> deserializer() {
        return new MQEventJsonDeserializer();
    }
}
