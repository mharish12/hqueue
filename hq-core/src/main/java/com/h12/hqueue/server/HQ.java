package com.h12.hqueue.server;

import com.h12.hqueue.di.DependencyManager;

import java.io.IOException;

public class HQ {
    private AppContext appContext;
    private static DependencyManager dependencyManager;

    public HQ() {
        init();
    }

    private void init() {
        try {
            appContext = new AppContext();
            dependencyManager = new DependencyManager(appContext);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        dependencyManager.start();
    }

    protected AppContext getAppContext() {
        return this.appContext;
    }

    protected DependencyManager getDependencyManager() {
        return HQ.dependencyManager;
    }
}
