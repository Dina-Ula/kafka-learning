package com.lbg.serdes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lbg.model.Event;
import com.lbg.model.FPSPayment;

public class FPSPaymentEventJsonDeserializer extends EventJsonDeserializer<FPSPayment> {

    public FPSPaymentEventJsonDeserializer() {
        super(new TypeReference<Event<FPSPayment>>() {
        });
    }
}
