package com.h12.hqueue.server;

import com.h12.hqueue.di.DependencyManager;

import java.io.IOException;

public class HQContext implements IContext {
    private static AppContext appContext;
    private static DependencyManager dependencyManager;

    protected HQContext() throws IOException {
        init();
    }

    private void init() throws IOException {
        HQContext.appContext = new AppContext();
        HQContext.dependencyManager = new DependencyManager(appContext);
    }

    @Override
    public void start() {
        appContext.start();
        dependencyManager.start();
    }

    protected AppContext getAppContext() {
        return appContext;
    }

    protected DependencyManager getDependencyManager() {
        return dependencyManager;
    }
}
