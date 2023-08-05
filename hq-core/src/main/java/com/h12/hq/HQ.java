package com.h12.hq;

import com.h12.hq.exception.HQException;

import java.io.IOException;

public class HQ {
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
        init();
        HQ.hqContext.start();
        Runtime.getRuntime().gc();
    }

    public static void stop() {
        HQ.hqContext.stop();
    }
}
