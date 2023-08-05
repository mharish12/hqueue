package com.h12.hq;

public class BeanManager extends AbstractManager {
    private static IContext iContext;

    public BeanManager() {
        iContext = new BeanContext();
    }

    @Override
    public void prepare(DependencyManager dependencyManager) {
        iContext.prepare(dependencyManager);
    }

    @Override
    public void start() {
        iContext.start();
    }

    @Override
    public IContext getContext() {
        return iContext;
    }
}
