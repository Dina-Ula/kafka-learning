package com.lbg.service;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.ReferenceFPSSortCode;
import com.lbg.util.ReferenceDataValidator;

public class FPSPaymentConsumerV2 extends ExceptionHandledEventConsumerV2<FPSPayment, ReferenceFPSSortCode> {

    private final ReferenceDataValidator referenceDataValidator;

    public FPSPaymentConsumerV2(final ReferenceDataValidator referenceDataValidator) {
        this.referenceDataValidator = referenceDataValidator;
    }

    @Override
    public void consumeEvent(final Event<FPSPayment> event) {
        System.out.println("This is FPSPaymentConsumerV2");
        referenceDataValidator.validateReferenceFPSSortCode1(event.getData());
    }
}
