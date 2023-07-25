package com.h12.hq;

import com.h12.hq.di.DependencyManager;

import java.io.IOException;

public class HQContext {
    private static AppContext appContext;
    private static DependencyManager dependencyManager;

    protected HQContext() throws IOException {
        init();
    }

    private void init() throws IOException {
        HQContext.appContext = new AppContext();
        HQContext.dependencyManager = new DependencyManager(appContext);
    }

    public void start() {
        appContext.start();
        dependencyManager.start();
    }

    public void stop() {
        appContext.stop();
        dependencyManager.stop();
    }

    protected AppContext getAppContext() {
        return appContext;
    }

    protected DependencyManager getDependencyManager() {
        return dependencyManager;
    }
}
