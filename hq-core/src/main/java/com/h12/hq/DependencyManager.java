package com.h12.hq;

import com.h12.hq.AppResource;
import com.h12.hq.IManager;
import com.h12.hq.di.impl.DIManagerImpl;
import com.h12.hq.hooks.ShutDownHookManager;
import com.h12.hq.AppContext;
import com.h12.hq.server.http.impl.RouteManager;
import io.github.classgraph.ScanResult;

import java.io.IOException;
import java.io.Serializable;

public class DependencyManager implements Serializable {
    private final AppContext appContext;
    private final IManager diManager;
    private final IManager shutDownHookManager;
    private final IManager routeManager;

    public DependencyManager() throws IOException {
        this(new AppContext());
    }

    public DependencyManager(AppContext appContext) {
        this.appContext = appContext;
        this.diManager = new DIManagerImpl();
        this.shutDownHookManager = new ShutDownHookManager();
        this.routeManager = new RouteManager();
        this.diManager.prepare(this);
        this.shutDownHookManager.prepare(this);
        this.routeManager.prepare(this);
    }

    void start() {
        this.diManager.start();
        this.shutDownHookManager.start();
        this.routeManager.start();
    }

    void stop() {
        this.diManager.stop();
        this.shutDownHookManager.stop();
        this.routeManager.stop();
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public ScanResult getScanResult() {
        return ((AppResource)appContext.getResource()).getScanResult();
    }

    public void setScanResult(ScanResult scanResult) {
        ((AppResource)appContext.getResource()).setScanResult(scanResult);
    }
}
