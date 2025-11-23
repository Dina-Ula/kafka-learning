package com.lbg.service;

import com.lbg.model.Event;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ExceptionHandledEventConsumerV3<T, U, V> extends ExceptionHandledEventConsumer<T> implements Function<GlobalKTable<String, Event<U>>, Function<GlobalKTable<String, Event<V>>, Consumer<KStream<byte[], Event<T>>>>> {

    @Override
    public Function<GlobalKTable<String, Event<V>>, Consumer<KStream<byte[], Event<T>>>> apply(final GlobalKTable<String, Event<U>> globalKTable) {
        return g -> this::accept;
    }
}
