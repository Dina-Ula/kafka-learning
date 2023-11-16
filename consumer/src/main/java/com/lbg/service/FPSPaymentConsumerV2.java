package com.lbg.service;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.ReferenceFPSSortCode;
import com.lbg.util.ReferenceDataValidator;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;

import java.util.function.BiConsumer;

public class FPSPaymentConsumerV2 implements BiConsumer<KStream<byte[], Event<FPSPayment>>, GlobalKTable<String, Event<ReferenceFPSSortCode>>> {

    private final ReferenceDataValidator referenceDataValidator;

    public FPSPaymentConsumerV2(final ReferenceDataValidator referenceDataValidator) {
        this.referenceDataValidator = referenceDataValidator;
    }

    @Override
    public void accept(final KStream<byte[], Event<FPSPayment>> eventKStream, final GlobalKTable<String, Event<ReferenceFPSSortCode>> stringEventGlobalKTable) {
        eventKStream.peek((key, value) -> {
            System.out.println("Event consumed: " + value);
        }).foreach((key, value) -> {
            try {
                referenceDataValidator.validateReferenceFPSSortCode1(value.getData());
            } catch (Exception e) {
                System.out.println("Exception Handled Event Consumer: " + e);
            }
        });
    }
}
