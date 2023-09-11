package com.h12.hq;

import com.h12.hq.di.impl.DIManagerImpl;
import com.h12.hq.server.ServerManager;
import io.github.classgraph.ScanResult;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class DependencyManager implements Serializable {
    private final AppContext appContext;
    private final Map<String, IManager> managers;

    public DependencyManager() throws IOException {
        this(new AppContext());
    }

    public DependencyManager(AppContext appContext) {
        this.managers = new TreeMap<>();
        this.appContext = appContext;
        managers.put(DIManagerImpl.class.getName(), new DIManagerImpl());
        managers.put(ServerManager.class.getName(), new ServerManager());
    }

    void prepare() {
        this.appContext.start();
        for (Map.Entry<String, IManager> managerEntry : managers.entrySet()) {
            managerEntry.getValue().prepare(this);
        }
    }

    void start() {
        for (Map.Entry<String, IManager> managerEntry : managers.entrySet()) {
            managerEntry.getValue().start();
        }
    }

    void stop() {
        for (Map.Entry<String, IManager> managerEntry : managers.entrySet()) {
            managerEntry.getValue().stop();
        }
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
