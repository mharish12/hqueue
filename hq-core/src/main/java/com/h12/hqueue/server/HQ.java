package com.h12.hqueue.server;

import com.h12.hqueue.di.DependencyManager;

public class HQ {
    private final DependencyManager dependencyManager;

    public HQ() {
        dependencyManager = new DependencyManager();
    }

    public static void start() {

    }
}
