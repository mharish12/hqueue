package com.h12.hq.server;

import com.h12.hq.AppContext;
import com.h12.hq.IContext;
import com.h12.hq.di.DependencyManager;
import com.h12.hq.server.exception.ServerException;
import com.h12.hq.server.http.IServer;
import com.h12.hq.util.Constants;
import com.h12.hq.util.ReflectionUtil;

public class ServerContext implements IContext {
    private IServer iServer;

    @Override
    public void prepare(DependencyManager dependencyManager) {
        String serverClassString = dependencyManager.getAppContext().getEnvironment().getProperty(Constants.SERVER_CLASS);
        if(serverClassString == null) {
            serverClassString = Constants.DEFAULT_SERVER_CLASS;
        }
        try {
            iServer = (IServer) ReflectionUtil.newInstance(serverClassString);
        } catch (ClassNotFoundException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public void start() {
        iServer.start();
    }

    @Override
    public void stop() {
        iServer.stop();
    }
}
