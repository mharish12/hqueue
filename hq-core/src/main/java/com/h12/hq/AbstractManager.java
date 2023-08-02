package com.h12.hq;

public abstract class AbstractManager implements IManager {

    @Override
    public void stop() {}

    @Override
    public IContext getContext() {
        return null;
    }
}
