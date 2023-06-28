package com.h12.hqueue.di;

import com.h12.hqueue.IManager;
import com.h12.hqueue.hooks.ShutDownHookManager;
import com.h12.hqueue.server.AppContext;

import java.io.IOException;
import java.io.Serializable;

public class DependencyManager implements Serializable {
    private final AppContext appContext;
    private final IManager diManager;
    private final IManager shutDownHookManager;

    public DependencyManager() throws IOException {
        this(new AppContext());
    }

    public DependencyManager(AppContext appContext) {
        this.appContext = appContext;
        this.diManager = new DIManager();
        this.shutDownHookManager = new ShutDownHookManager();
    }

    public void start() {
        System.out.println("Server port: " + appContext.getEnvironment().getProperty("server.port"));
        this.diManager.prepare(appContext);
        this.shutDownHookManager.prepare(appContext);
        this.diManager.start();
        this.shutDownHookManager.start();
    }

    public void stop() {
        this.diManager.stop();
        this.shutDownHookManager.stop();
    }
}
