package com.lbg.util;

import com.lbg.model.Event;
import com.lbg.model.SettlementCycle;

public class SettlementCycleCalculator {

    private SettlementCycleDataKTables settlementCycleDataKTables;

    public SettlementCycleCalculator(SettlementCycleDataKTables settlementCycleDataKTables) {
        this.settlementCycleDataKTables = settlementCycleDataKTables;
    }

    public void getSettlementCycle() {

        final Event<SettlementCycle> settlementCycle =
                settlementCycleDataKTables.getSettlementCycle().get("SETTLEMENT_CYCLE");
        System.out.println("Settlement Cycle: " + settlementCycle);
    }

    public void getSettlementCycleDedupe() {
        final Event<SettlementCycle> settlementCycle =
                settlementCycleDataKTables.getSettlementCycleDedupe().get("SETTLEMENT_CYCLE");
        System.out.println("Settlement Cycle: " + settlementCycle);
    }
}
