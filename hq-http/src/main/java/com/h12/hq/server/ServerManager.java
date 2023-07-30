package com.h12.hq.server;

import com.h12.hq.IContext;
import com.h12.hq.IManager;
import com.h12.hq.di.DependencyManager;

public class ServerManager implements IManager {
    private IContext iContext;

    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.iContext = new ServerContext();
        this.iContext.prepare(dependencyManager);
    }

    @Override
    public void start() {
        this.iContext.start();
    }

    @Override
    public void stop() {
        this.iContext.stop();
    }

    @Override
    public IContext getContext() {
        return iContext;
    }
}
