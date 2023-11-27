package com.lbg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementCycle {

    private String settlementDate;
    private String settlementCycle;
}
