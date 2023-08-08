package com.h12.hq.server;

import com.h12.hq.DependencyManager;
import com.h12.hq.IContext;
import com.h12.hq.IManager;
import com.h12.hq.server.http.RouteManager;

public class ServerManager implements IManager {
    private final IContext iContext;
    private final IManager routeManager;

    public ServerManager() {
        this.routeManager = new RouteManager();
        this.iContext = new ServerContext();
    }

    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.routeManager.prepare(dependencyManager);
        this.iContext.prepare(dependencyManager);
    }

    @Override
    public void start() {
        this.routeManager.start();
        this.iContext.start();
    }

    @Override
    public void stop() {
        this.routeManager.stop();
        this.iContext.stop();
    }

    @Override
    public IContext getContext() {
        return iContext;
    }
}
