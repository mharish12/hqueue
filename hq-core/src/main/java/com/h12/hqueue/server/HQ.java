package com.h12.hqueue.server;

import java.io.IOException;

public class HQ {
    private static IContext hqContext;

    public HQ() {
        init();
    }

    private void init() {
        try {
            HQ.hqContext = new HQContext();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() {
        HQ.hqContext.start();
    }
}
