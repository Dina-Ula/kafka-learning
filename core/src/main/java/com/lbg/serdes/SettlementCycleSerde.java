package com.lbg.serdes;

import com.lbg.model.Event;
import com.lbg.model.SettlementCycle;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class SettlementCycleSerde implements Serde<Event<SettlementCycle>> {

    @Override
    public Serializer<Event<SettlementCycle>> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<Event<SettlementCycle>> deserializer() {
        return new SettlementCycleEventJsonDeserializer();
    }
}
