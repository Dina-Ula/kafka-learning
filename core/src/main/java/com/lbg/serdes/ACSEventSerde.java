package com.lbg.serdes;

import com.lbg.model.ACSEvent;
import com.lbg.model.Event;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@NoArgsConstructor
public class ACSEventSerde implements Serde<Event<ACSEvent>> {

    @Override
    public Serializer<Event<ACSEvent>> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<Event<ACSEvent>> deserializer() {
        return new ACSEventJsonDeserializer();
    }
}
