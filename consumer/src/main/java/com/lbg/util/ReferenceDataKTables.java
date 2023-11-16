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

    public ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> getReferenceFPSSortCode1() {
        return lbgInteractiveQueryService.getQueryableStore("reference-fps-sort-codes-store-1", QueryableStoreTypes.keyValueStore());
    }

    public ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> getReferenceFPSSortCode2() {
        return lbgInteractiveQueryService.getQueryableStore("reference-fps-sort-codes-store-2", QueryableStoreTypes.keyValueStore());
    }

    public ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> getReferenceFPSSortCode3() {
        return lbgInteractiveQueryService.getQueryableStore("reference-fps-sort-codes-store-3", QueryableStoreTypes.keyValueStore());
    }

    public ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> getReferenceFPSSortCode4() {
        return lbgInteractiveQueryService.getQueryableStore("reference-fps-sort-codes-store-4", QueryableStoreTypes.keyValueStore());
    }

    public ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> getReferenceFPSSortCode5() {
        return lbgInteractiveQueryService.getQueryableStore("reference-fps-sort-codes-store-5", QueryableStoreTypes.keyValueStore());
    }

    public ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> getReferenceFPSSortCode6() {
        return lbgInteractiveQueryService.getQueryableStore("reference-fps-sort-codes-store-6", QueryableStoreTypes.keyValueStore());
    }

    public ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> getReferenceFPSSortCode7() {
        return lbgInteractiveQueryService.getQueryableStore("reference-fps-sort-codes-store-7", QueryableStoreTypes.keyValueStore());
    }

    public ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> getReferenceFPSSortCode8() {
        return lbgInteractiveQueryService.getQueryableStore("reference-fps-sort-codes-store-8", QueryableStoreTypes.keyValueStore());
    }
}
