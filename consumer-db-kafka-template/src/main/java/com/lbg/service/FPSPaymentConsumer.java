package com.lbg.service;

import com.lbg.dao.FPSPaymentRepository;
import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.FPSPaymentEntity;
import com.lbg.model.ReferenceFPSSortCode;
import com.lbg.util.ReferenceDataValidator;

public class FPSPaymentConsumer extends ExceptionHandledEventConsumerV2<FPSPayment, ReferenceFPSSortCode> {

    private final FPSPaymentRepository fpsPaymentRepository;
    private final ReferenceDataValidator referenceDataValidator;

    public FPSPaymentConsumer(final FPSPaymentRepository fpsPaymentRepository, final ReferenceDataValidator referenceDataValidator) {
        this.fpsPaymentRepository = fpsPaymentRepository;
        this.referenceDataValidator = referenceDataValidator;
    }

    @Override
    public void consumeEvent(final Event<FPSPayment> event) {

        final boolean isValid = referenceDataValidator.validateReferenceFPSSortCode(event.getData());
        if (isValid) {
            fpsPaymentRepository.save(new FPSPaymentEntity(event.getData().getAccountNo(), event.getData().getSortCode()));
        }
    }
}
