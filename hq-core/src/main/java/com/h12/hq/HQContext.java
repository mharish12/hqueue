package com.h12.hq;

import com.h12.hq.di.DependencyManager;
import com.h12.hq.hooks.ShutDownHookManager;

import java.io.IOException;

public class HQContext {
    private static AppContext appContext;
    private static DependencyManager dependencyManager;
    private static ShutDownHookManager shutDownHookManager;

    protected HQContext() throws IOException {
        init();
    }

    private void init() throws IOException {
        HQContext.appContext = new AppContext();
        HQContext.dependencyManager = new DependencyManager(appContext);
        HQContext.shutDownHookManager = new ShutDownHookManager();
        shutDownHookManager.prepare(dependencyManager);
    }

    public void start() {
        appContext.start();
        dependencyManager.start();
        shutDownHookManager.start();
    }

    public void stop() {
        appContext.stop();
        dependencyManager.stop();
        shutDownHookManager.stop();
    }

    protected AppContext getAppContext() {
        return appContext;
    }

    protected DependencyManager getDependencyManager() {
        return dependencyManager;
    }
}
