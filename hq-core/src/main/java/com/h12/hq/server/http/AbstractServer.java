package com.h12.hq.server.http;

import com.h12.hq.AppContext;
import com.h12.hq.IResource;
import com.h12.hq.hooks.ShutDownHook;
import com.h12.hq.util.Config;

public abstract class AbstractServer extends ShutDownHook implements IServer {
    public int getPort(AppContext appContext) {
        return Integer.parseInt(appContext.getEnvironment().getProperty(Config.SERVER_PORT, Config.DEFAULT_SERVER_PORT));
    }

    public String getHost(AppContext appContext) {
        return appContext.getEnvironment().getProperty(Config.SERVER_HOST, Config.DEFAULT_SERVER_HOST);
    }
}
