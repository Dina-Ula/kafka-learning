package com.lbg.service;

import com.lbg.model.Event;
import org.apache.kafka.streams.kstream.KStream;

public abstract class ExceptionHandledEventConsumer<T> implements EventConsumer<T> {

    @Override
    public void accept(KStream<byte[], Event<T>> stream) {
        stream.peek((key, value) -> {
            System.out.println("Event consumed: " + value);
        }).foreach((key, value) -> {
            try {
                consumeEvent(value);
            } catch (Exception e) {
                System.out.println("Exception Handled Event Consumer: " + e);
            }
        });
    }
}
