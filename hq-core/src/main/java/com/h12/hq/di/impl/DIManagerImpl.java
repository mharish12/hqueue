package com.h12.hq.di.impl;

import com.h12.hq.DependencyManager;
import com.h12.hq.IContext;
import com.h12.hq.di.DIBeanContext;
import com.h12.hq.di.DIManager;

public class DIManagerImpl extends DIManager {
    private final DIBeanContext diBeanContext;

    public DIManagerImpl() {
        super();
        this.diBeanContext = new DIBeanContext();
    }

    @Override
    public void prepare(DependencyManager dependencyManager) {
        scanPackages(dependencyManager);
        this.diBeanContext.prepare(dependencyManager);
    }

    @Override
    public void start() {
        diBeanContext.start();
    }

    @Override
    public void stop() {
        diBeanContext.stop();
    }

    @Override
    public IContext getContext() {
        return diBeanContext;
    }
}
