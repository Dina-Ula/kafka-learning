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

    public boolean validateReferenceFPSSortCode3(FPSPayment payment) {
        final Event<ReferenceFPSSortCode> referenceFPSSortCode =
                referenceDataKTables.getReferenceFPSSortCode3().get(payment.getSortCode());

        System.out.println("Found in Reference FPS Sort Code 3: " + referenceFPSSortCode);

        return Objects.nonNull(referenceFPSSortCode);
    }

    public boolean validateReferenceFPSSortCode4(FPSPayment payment) {
        final Event<ReferenceFPSSortCode> referenceFPSSortCode =
                referenceDataKTables.getReferenceFPSSortCode4().get(payment.getSortCode());

        System.out.println("Found in Reference FPS Sort Code 4: " + referenceFPSSortCode);

        return Objects.nonNull(referenceFPSSortCode);
    }

    public boolean validateReferenceFPSSortCode5(FPSPayment payment) {
        final Event<ReferenceFPSSortCode> referenceFPSSortCode =
                referenceDataKTables.getReferenceFPSSortCode5().get(payment.getSortCode());

        System.out.println("Found in Reference FPS Sort Code 5: " + referenceFPSSortCode);

        return Objects.nonNull(referenceFPSSortCode);
    }

    public boolean validateReferenceFPSSortCode6(FPSPayment payment) {
        final Event<ReferenceFPSSortCode> referenceFPSSortCode =
                referenceDataKTables.getReferenceFPSSortCode6().get(payment.getSortCode());

        System.out.println("Found in Reference FPS Sort Code 6: " + referenceFPSSortCode);

        return Objects.nonNull(referenceFPSSortCode);
    }

    public boolean validateReferenceFPSSortCode7(FPSPayment payment) {
        final Event<ReferenceFPSSortCode> referenceFPSSortCode =
                referenceDataKTables.getReferenceFPSSortCode7().get(payment.getSortCode());

        System.out.println("Found in Reference FPS Sort Code 7: " + referenceFPSSortCode);

        return Objects.nonNull(referenceFPSSortCode);
    }

    public boolean validateReferenceFPSSortCode8(FPSPayment payment) {
        final Event<ReferenceFPSSortCode> referenceFPSSortCode =
                referenceDataKTables.getReferenceFPSSortCode8().get(payment.getSortCode());

        System.out.println("Found in Reference FPS Sort Code 8: " + referenceFPSSortCode);

        return Objects.nonNull(referenceFPSSortCode);
    }
}
