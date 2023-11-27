package com.lbg.serdes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lbg.model.Event;
import com.lbg.model.SettlementCycle;

public class SettlementCycleEventJsonDeserializer extends EventJsonDeserializer<SettlementCycle> {

    public SettlementCycleEventJsonDeserializer() {
        super(new TypeReference<Event<SettlementCycle>>() {
        });
    }
}
