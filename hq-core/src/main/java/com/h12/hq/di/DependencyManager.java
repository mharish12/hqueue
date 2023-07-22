package com.h12.hq.di;

import com.h12.hq.IManager;
import com.h12.hq.di.impl.DIManagerImpl;
import com.h12.hq.hooks.ShutDownHookManager;
import com.h12.hq.server.AppContext;

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
        this.diManager = new DIManagerImpl();
        this.shutDownHookManager = new ShutDownHookManager();
    }

    public void start() {
//        System.out.println("Server port: " + appContext.getEnvironment().getProperty(Constants.SERVER_PORT));
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
