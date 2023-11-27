package com.lbg.service;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.SettlementCycle;
import com.lbg.util.SettlementCycleCalculator;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;

import java.util.function.BiConsumer;

public class DeDupeConsumerV1 implements BiConsumer<KStream<byte[], Event<FPSPayment>>, GlobalKTable<String, Event<SettlementCycle>>> {

    private final SettlementCycleCalculator settlementCycleCalculator;

    public DeDupeConsumerV1(final SettlementCycleCalculator settlementCycleCalculator) {
        this.settlementCycleCalculator = settlementCycleCalculator;
    }

    @Override
    public void accept(final KStream<byte[], Event<FPSPayment>> eventKStream, final GlobalKTable<String, Event<SettlementCycle>> globalKTable) {
        eventKStream.peek((key, value) -> {
            System.out.println("Event consumed: " + value);
        }).foreach((key, value) -> {
            try {
                settlementCycleCalculator.getSettlementCycle();
            } catch (Exception e) {
                System.out.println("Exception Handled Event Consumer: " + e);
            }
        });
    }
}
