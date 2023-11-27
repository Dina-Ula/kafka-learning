package com.lbg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FPSPayment {

    private String id;
    private String accountNo;
    private String sortCode;
    private String amount;
    private String settlementDate;
    private String settlementCycle;
}
