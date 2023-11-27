package com.lbg.util;

import org.apache.kafka.streams.state.QueryableStoreType;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;

public class LBGInteractiveQueryService {

    private InteractiveQueryService interactiveQueryService;

    public LBGInteractiveQueryService(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public synchronized <T> T getQueryableStore(String storeName, QueryableStoreType<T> storeType) {
        return this.interactiveQueryService.getQueryableStore(storeName, storeType);
    }
}
