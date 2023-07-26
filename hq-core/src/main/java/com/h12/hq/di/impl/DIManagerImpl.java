package com.h12.hq.di.impl;

import com.h12.hq.AppContext;
import com.h12.hq.di.DIBeanManager;
import com.h12.hq.di.DIManager;
import com.h12.hq.di.DependencyManager;

public class DIManagerImpl  extends DIManager {
    private AppContext appContext;
    private DIBeanManager diBeanManager;

    public DIManagerImpl() {
        super();
        this.diBeanManager = new DIBeanManager();
    }

    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.appContext = dependencyManager.getAppContext();
        if(appContext.getScanResult() == null) {
            scanPackages(appContext);
        }
    }

    @Override
    public void start() {
        diBeanManager.start();
    }

    @Override
    public void stop() {
        diBeanManager.stop();
    }
}
