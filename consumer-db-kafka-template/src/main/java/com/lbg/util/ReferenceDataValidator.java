package com.lbg.util;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.ReferenceFPSSortCode;

import java.util.Objects;

public class ReferenceDataValidator {

    private final ReferenceDataKTables referenceDataKTables;

    public ReferenceDataValidator(ReferenceDataKTables referenceDataKTables) {
        this.referenceDataKTables = referenceDataKTables;
    }

    public boolean validateReferenceFPSSortCode(FPSPayment payment) {

        final Event<ReferenceFPSSortCode> referenceFPSSortCode =
                referenceDataKTables.getReferenceFPSSortCode().get(payment.getSortCode());

        System.out.println("Found in Reference FPS Sort Code: " + referenceFPSSortCode);

        return Objects.nonNull(referenceFPSSortCode);
    }
}
