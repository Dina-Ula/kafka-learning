package com.lbg.serdes;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@NoArgsConstructor
public class FPSPaymentSerde implements Serde<Event<FPSPayment>> {

    @Override
    public Serializer<Event<FPSPayment>> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<Event<FPSPayment>> deserializer() {
        return new FPSPaymentEventJsonDeserializer();
    }
}
