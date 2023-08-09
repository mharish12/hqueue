package com.h12.hq;

import com.h12.hq.exception.HQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class HQ {
    private static final Logger LOGGER = LoggerFactory.getLogger(HQ.class);
    private static HQContext hqContext;

    private static void init() {
        try {
            if(HQ.hqContext == null) {
                HQ.hqContext = new HQContext();
            } else {
                throw new HQException("Trying to create multiple sessions.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() {
        Instant start = Instant.now();
        init();
        HQ.hqContext.start();
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        LOGGER.info("Application start up time in seconds: {} seconds", duration.getSeconds()/1000000000.0);
        Runtime.getRuntime().gc();
    }

    public static void stop() {
        HQ.hqContext.stop();
    }
}
