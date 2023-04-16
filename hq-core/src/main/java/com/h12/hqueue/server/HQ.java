package com.h12.hqueue.server;

import com.h12.hqueue.di.DependencyManager;

public class HQ {
    private final AppContext appContext;
    private static DependencyManager dependencyManager;

    public HQ() {
        appContext = new AppContext();
        dependencyManager = new DependencyManager(appContext);
    }

    public static void start() {
        dependencyManager.start();
    }
}
