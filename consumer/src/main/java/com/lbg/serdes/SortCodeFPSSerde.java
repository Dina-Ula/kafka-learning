package com.lbg.serdes;

import com.lbg.model.Event;
import com.lbg.model.ReferenceFPSSortCode;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@NoArgsConstructor
public class SortCodeFPSSerde implements Serde<Event<ReferenceFPSSortCode>> {

    @Override
    public Serializer<Event<ReferenceFPSSortCode>> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<Event<ReferenceFPSSortCode>> deserializer() {
        return new SortCodeFPSEventJsonDeserializer();
    }
}
