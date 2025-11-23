package com.lbg.util;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.ReferenceFPSSortCode;

import java.util.Objects;

public class ReferenceDataValidator {

    private ReferenceDataKTables referenceDataKTables;

    public ReferenceDataValidator(ReferenceDataKTables referenceDataKTables) {
        this.referenceDataKTables = referenceDataKTables;
    }

    public boolean validateReferenceFPSSortCode1(FPSPayment payment) {

        final Event<ReferenceFPSSortCode> referenceFPSSortCode =
                referenceDataKTables.getReferenceFPSSortCode1().get(payment.getSortCode());

        System.out.println("Found in Reference FPS Sort Code 1: " + referenceFPSSortCode);

        return Objects.nonNull(referenceFPSSortCode);
    }

    public boolean validateReferenceFPSSortCode2(FPSPayment payment) {
        final Event<ReferenceFPSSortCode> referenceFPSSortCode =
                referenceDataKTables.getReferenceFPSSortCode2().get(payment.getSortCode());

        System.out.println("Found in Reference FPS Sort Code 2: " + referenceFPSSortCode);

        return Objects.nonNull(referenceFPSSortCode);
    }
}
