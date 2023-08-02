package com.h12.hq;

public abstract class AbstractContext implements IContext {
    @Override
    public void stop() {}

    @Override
    public IResource getResource() {
        return null;
    }
}
