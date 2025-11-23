package com.lbg.consumer.config;

import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomStreamsExceptionHandler implements StreamsUncaughtExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomStreamsExceptionHandler.class);

    @Override
    public StreamThreadExceptionResponse handle(Throwable exception) {
        log.error("Uncaught exception in Kafka Streams: ", exception);

        // Decide what action to take:
        // 1. Replace with `REPLACE_THREAD` to restart the thread
        // 2. Use `SHUTDOWN_APPLICATION` to stop the app
        return StreamThreadExceptionResponse.SHUTDOWN_APPLICATION;
    }
}

