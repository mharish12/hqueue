package com.h12.hq.di;

import com.h12.hq.AbstractManager;
import com.h12.hq.DependencyManager;
import com.h12.hq.IContext;

public class BeanManager extends AbstractManager {
    private IContext iContext;

    @Override
    public void prepare(DependencyManager dependencyManager) {
        iContext = new DIBeanContext();
        iContext.prepare(dependencyManager);
    }

    @Override
    public void start() {
        iContext.start();
    }

    @Override
    public void stop() {
        iContext.stop();
    }

    @Override
    public IContext getContext() {
        return iContext;
    }
}
