package com.h12.hqueue.server;

import com.h12.hqueue.di.DependencyManager;

import java.io.IOException;

public class HQ {
    private IContext hqContext;

    public HQ() {
        init();
    }

    private void init() {
        try {
            hqContext = new HQContext();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        hqContext.start();
    }
}
