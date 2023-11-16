package com.lbg.service;

import com.lbg.model.Event;
import org.apache.kafka.streams.kstream.KStream;

import java.util.function.Consumer;

public interface EventConsumer<T> extends Consumer<KStream<byte[], Event<T>>> {

    default void accept(KStream<byte[], Event<T>> stream) {
        stream.peek((key, value) -> {
            System.out.println("Event consumed: " + value);
        }).foreach((key, value) -> {
            consumeEvent(value);
        });
    }

    void consumeEvent(Event<T> event);
}
