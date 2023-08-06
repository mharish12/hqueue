package com.h12.hq;

import com.h12.hq.di.impl.DIManagerImpl;
import com.h12.hq.server.http.impl.RouteManager;
import io.github.classgraph.ScanResult;

import java.io.IOException;
import java.io.Serializable;

public class DependencyManager implements Serializable {
    private final AppContext appContext;
    private final IManager diManager;
    private final IManager routeManager;

    public DependencyManager() throws IOException {
        this(new AppContext());
    }

    public DependencyManager(AppContext appContext) {
        this.appContext = appContext;
        this.diManager = new DIManagerImpl();
        this.routeManager = new RouteManager();
    }

    void prepare() {
        this.diManager.prepare(this);
        this.routeManager.prepare(this);
        this.appContext.start();
    }

    void start() {
        this.diManager.start();
        this.routeManager.start();
    }

    void stop() {
        this.diManager.stop();
        this.routeManager.stop();
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public ScanResult getScanResult() {
        return appContext.getScanResult();
    }

    public void setScanResult(ScanResult scanResult) {
        appContext.setScanResult(scanResult);
    }
}
