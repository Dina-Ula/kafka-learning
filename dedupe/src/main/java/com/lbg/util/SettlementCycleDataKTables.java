package com.lbg.util;

import com.lbg.model.Event;
import com.lbg.model.SettlementCycle;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

public class SettlementCycleDataKTables {

    private LBGInteractiveQueryService lbgInteractiveQueryService;

    public SettlementCycleDataKTables(LBGInteractiveQueryService lbgInteractiveQueryService) {
        this.lbgInteractiveQueryService = lbgInteractiveQueryService;
    }

    public ReadOnlyKeyValueStore<String, Event<SettlementCycle>> getSettlementCycle() {
        return lbgInteractiveQueryService.getQueryableStore("settlement-cycle", QueryableStoreTypes.keyValueStore());
    }

    public ReadOnlyKeyValueStore<String, Event<SettlementCycle>> getSettlementCycleDedupe() {
        return lbgInteractiveQueryService.getQueryableStore("settlement-cycle-dedupe", QueryableStoreTypes.keyValueStore());
    }
}
