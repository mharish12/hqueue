package com.h12.hq.server.http;

import com.h12.hq.DependencyManager;
import com.h12.hq.IResource;
import com.h12.hq.AppContext;
import com.h12.hq.hooks.ShutDownHook;
import com.h12.hq.util.Constants;

public abstract class AbstractServer extends ShutDownHook implements IServer, IResource {
    public int getPort(AppContext appContext) {
        return Integer.parseInt(appContext.getEnvironment().getProperty(Constants.SERVER_PORT, Constants.DEFAULT_SERVER_PORT));
    }

    public String getHost(AppContext appContext) {
        return appContext.getEnvironment().getProperty(Constants.SERVER_HOST, Constants.DEFAULT_SERVER_HOST);
    }

    public abstract void prepare(DependencyManager dependencyManager);
}
