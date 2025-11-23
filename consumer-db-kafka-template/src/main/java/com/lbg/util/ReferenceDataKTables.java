package com.lbg.util;

import com.lbg.model.Event;
import com.lbg.model.ReferenceFPSSortCode;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

public class ReferenceDataKTables {

    private LBGInteractiveQueryService lbgInteractiveQueryService;

    public ReferenceDataKTables(LBGInteractiveQueryService lbgInteractiveQueryService) {
        this.lbgInteractiveQueryService = lbgInteractiveQueryService;
    }

    public ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> getReferenceFPSSortCode() {
        return lbgInteractiveQueryService.getQueryableStore("reference-fps-sort-codes-store", QueryableStoreTypes.keyValueStore());
    }
}
