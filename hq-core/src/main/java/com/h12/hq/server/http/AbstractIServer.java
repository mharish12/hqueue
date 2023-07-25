package com.h12.hq.server.http;

import com.h12.hq.hooks.IShutDownHook;
import com.h12.hq.AppContext;
import com.h12.hq.util.Constants;


public abstract class AbstractIServer implements IServer, IShutDownHook {
    public int getPort(AppContext appContext) {
        return Integer.parseInt(appContext.getEnvironment().getProperty(Constants.SERVER_PORT, Constants.DEFAULT_SERVER_PORT));
    }

    public String getHost(AppContext appContext) {
        return appContext.getEnvironment().getProperty(Constants.SERVER_CLASS, Constants.DEFAULT_SERVER_HOST);
    }
}
