package com.h12.hq.server;

import java.io.IOException;

public class HQ {
    private static IContext hqContext;

    private static void init() {
        try {
            HQ.hqContext = new HQContext();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() {
        init();
        HQ.hqContext.start();
    }
}
