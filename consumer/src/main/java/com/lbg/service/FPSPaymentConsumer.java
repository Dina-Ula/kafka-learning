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
        System.out.println("This is FPSPaymentConsumer");
        System.out.println("Sleeping for 2 Seconds");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        referenceDataValidator.validateReferenceFPSSortCode1(event.getData());
        referenceDataValidator.validateReferenceFPSSortCode2(event.getData());
    }
}
