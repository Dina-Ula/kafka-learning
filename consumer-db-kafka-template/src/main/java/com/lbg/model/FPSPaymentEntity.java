package com.lbg.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "fps_payment")
public class FPSPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "sort_code")
    private String sortCode;

    public FPSPaymentEntity(String accountNo, String sortCode) {
        this.accountNo = accountNo;
        this.sortCode = sortCode;
    }
}
