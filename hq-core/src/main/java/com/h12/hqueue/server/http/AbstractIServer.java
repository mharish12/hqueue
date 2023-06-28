package com.h12.hqueue.server.http;

import com.h12.hqueue.hooks.IShutDownHook;
import com.h12.hqueue.server.AppContext;
import com.h12.hqueue.util.Constants;
import java.util.Properties;


public abstract class AbstractIServer implements IServer, IShutDownHook {
    public int getPort(AppContext appContext) {
        return Integer.parseInt(appContext.getEnvironment().getProperty(Constants.SERVER_PORT, Constants.DEFAULT_SERVER_PORT));
    }

    public String getHost(AppContext appContext) {
        return appContext.getEnvironment().getProperty(Constants.SERVER_HOST, Constants.DEFAULT_SERVER_HOST);
    }
}
