package com.lbg.serdes;

import com.lbg.model.Event;
import com.lbg.model.USMEvent;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@NoArgsConstructor
public class USMEventSerde implements Serde<Event<USMEvent>> {

    @Override
    public Serializer<Event<USMEvent>> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<Event<USMEvent>> deserializer() {
        return new USMEventJsonDeserializer();
    }
}
