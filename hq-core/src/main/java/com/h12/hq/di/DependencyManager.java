package com.h12.hq.di;

import com.h12.hq.IManager;
import com.h12.hq.di.impl.DIManagerImpl;
import com.h12.hq.hooks.ShutDownHookManager;
import com.h12.hq.AppContext;
import io.github.classgraph.ScanResult;

import java.io.IOException;
import java.io.Serializable;

public class DependencyManager implements Serializable {
    private final AppContext appContext;
    private final IManager diManager;
    private final IManager shutDownHookManager;
    private static ScanResult scanResult;

    public DependencyManager() throws IOException {
        this(new AppContext());
    }

    public DependencyManager(AppContext appContext) {
        this.appContext = appContext;
        this.diManager = new DIManagerImpl();
        this.shutDownHookManager = new ShutDownHookManager();
    }

    public void start() {
        this.diManager.prepare(this);
        this.shutDownHookManager.prepare(this);
        this.diManager.start();
        this.shutDownHookManager.start();
    }

    public void stop() {
        this.diManager.stop();
        this.shutDownHookManager.stop();
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        DependencyManager.scanResult = scanResult;
    }
}
