package com.lbg.model;

import jakarta.persistence.*;

import java.sql.Blob;
import java.sql.Date;

@Entity
public class FPSPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FPSPayment_SEQ")
    @SequenceGenerator(sequenceName = "FPSPayment_seq", allocationSize = 1, name = "FPSPayment_SEQ")
    private Long id;

    private Blob fpsPayment;

    @Column(name = "CREATED_DATE")
    private Date date;
}
