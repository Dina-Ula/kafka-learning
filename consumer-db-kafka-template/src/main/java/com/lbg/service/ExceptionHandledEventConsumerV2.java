package com.lbg.service;

import com.lbg.model.Event;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;

import java.util.function.BiConsumer;

public abstract class ExceptionHandledEventConsumerV2<T, U> extends ExceptionHandledEventConsumer<T> implements BiConsumer<KStream<byte[], Event<T>>, GlobalKTable<String, Event<U>>> {

    @Override
    public void accept(final KStream<byte[], Event<T>> eventKStream, final GlobalKTable<String, Event<U>> globalKTable) {
        this.accept(eventKStream);
    }
}
