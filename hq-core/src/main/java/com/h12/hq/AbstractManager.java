package com.h12.hq;

public abstract class AbstractManager extends AbstractPrepare implements IManager {

    @Override
    public IContext getContext() {
        return null;
    }
}
