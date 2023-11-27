package com.lbg.service;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.SettlementCycle;
import com.lbg.util.SettlementCycleCalculator;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;

import java.util.function.BiConsumer;

public class DeDupeConsumerV2 implements BiConsumer<KStream<byte[], Event<FPSPayment>>, GlobalKTable<String, Event<SettlementCycle>>> {

    private final SettlementCycleCalculator settlementCycleCalculator;

    public DeDupeConsumerV2(final SettlementCycleCalculator settlementCycleCalculator) {
        this.settlementCycleCalculator = settlementCycleCalculator;
    }

    @Override
    public void accept(final KStream<byte[], Event<FPSPayment>> eventKStream, final GlobalKTable<String, Event<SettlementCycle>> globalKTable) {
        eventKStream.peek((key, value) -> {
            System.out.println("Event consumed: " + value);
        }).foreach((key, value) -> {
            try {
                settlementCycleCalculator.getSettlementCycleDedupe();
            } catch (Exception e) {
                System.out.println("Exception Handled Event Consumer: " + e);
            }
        });
    }
}
