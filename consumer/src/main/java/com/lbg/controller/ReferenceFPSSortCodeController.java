package com.lbg.controller;

import com.lbg.model.Event;
import com.lbg.model.ReferenceFPSSortCode;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reference/fps/sort-code")
public class ReferenceFPSSortCodeController {

    private final InteractiveQueryService queryService;

    public ReferenceFPSSortCodeController(InteractiveQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/all")
    public List<Event<ReferenceFPSSortCode>> getAllReferenceFPSSortCodes(@RequestParam String storeId) {
        ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> keyValueStore =
                queryService.getQueryableStore("reference-fps-sort-codes-store-" + storeId,
                        QueryableStoreTypes.keyValueStore());

        final KeyValueIterator<String, Event<ReferenceFPSSortCode>> iterator = keyValueStore.all();

        List<Event<ReferenceFPSSortCode>> customerAccountData = new ArrayList<>();

        while (iterator.hasNext()) {
            final KeyValue<String, Event<ReferenceFPSSortCode>> keyValue = iterator.next();

            final Event<ReferenceFPSSortCode> value = keyValue.value;

            customerAccountData.add(value);
        }

        return customerAccountData;
    }

    @GetMapping("/count")
    public Integer getAllReferenceFPSSortCodesCount(@RequestParam String storeId) {
        ReadOnlyKeyValueStore<String, Event<ReferenceFPSSortCode>> keyValueStore =
                queryService.getQueryableStore("reference-fps-sort-codes-store-" + storeId,
                        QueryableStoreTypes.keyValueStore());

        final KeyValueIterator<String, Event<ReferenceFPSSortCode>> iterator = keyValueStore.all();

        int count = 0;

        while (iterator.hasNext()) {
            count++;
        }

        return count;
    }
}
