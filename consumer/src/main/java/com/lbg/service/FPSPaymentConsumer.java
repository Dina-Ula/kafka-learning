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
    }
}
