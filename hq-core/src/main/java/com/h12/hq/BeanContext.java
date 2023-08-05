package com.h12.hq;

import com.h12.hq.di.BeanFactory;

public class BeanContext extends AbstractContext {
    private static IResource iResource = null;

    public BeanContext() {
        iResource = new BeanFactory();
    }

    @Override
    public void prepare(DependencyManager dependencyManager) {
        iResource.prepare(dependencyManager);
    }

    @Override
    public void start() {
        iResource.start();
    }

    @Override
    public IResource getResource() {
        return iResource;
    }
}
