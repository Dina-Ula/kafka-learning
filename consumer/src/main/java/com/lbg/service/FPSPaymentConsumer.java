package com.lbg.service;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.util.ReferenceDataValidator;

public class FPSPaymentConsumer extends ExceptionHandledEventConsumer<FPSPayment> {

    private ReferenceDataValidator referenceDataValidator;

    public FPSPaymentConsumer(final ReferenceDataValidator referenceDataValidator) {
        this.referenceDataValidator = referenceDataValidator;
    }

    @Override
    public void consumeEvent(final Event<FPSPayment> event) {
        referenceDataValidator.validateReferenceFPSSortCode1(event.getData());
/*        referenceDataValidator.validateReferenceFPSSortCode2(event.getData());
        referenceDataValidator.validateReferenceFPSSortCode3(event.getData());
        referenceDataValidator.validateReferenceFPSSortCode4(event.getData());
        referenceDataValidator.validateReferenceFPSSortCode5(event.getData());
        referenceDataValidator.validateReferenceFPSSortCode6(event.getData());
        referenceDataValidator.validateReferenceFPSSortCode7(event.getData());
        referenceDataValidator.validateReferenceFPSSortCode8(event.getData());*/
    }
}
