package com.lbg.service;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.ReferenceFPSSortCode;
import com.lbg.util.ReferenceDataValidator;

public class FPSPaymentConsumerV3 extends ExceptionHandledEventConsumerV3<FPSPayment, ReferenceFPSSortCode, ReferenceFPSSortCode> {

    private final ReferenceDataValidator referenceDataValidator;

    public FPSPaymentConsumerV3(final ReferenceDataValidator referenceDataValidator) {
        this.referenceDataValidator = referenceDataValidator;
    }

    @Override
    public void consumeEvent(final Event<FPSPayment> event) {
        referenceDataValidator.validateReferenceFPSSortCode1(event.getData());
    }
}
