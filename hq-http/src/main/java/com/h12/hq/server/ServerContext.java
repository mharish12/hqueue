package com.h12.hq.server;

import com.h12.hq.IContext;
import com.h12.hq.DependencyManager;
import com.h12.hq.server.exception.ServerException;
import com.h12.hq.IResource;
import com.h12.hq.server.http.IServer;
import com.h12.hq.util.Constants;
import com.h12.hq.util.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ServerContext implements IContext {
    private IServer iServer;

    @Override
    public void prepare(DependencyManager dependencyManager) {
        String serverClassString = dependencyManager.getAppContext().getEnvironment().getProperty(Constants.SERVER_CLASS);
        if(serverClassString == null) {
            serverClassString = Constants.DEFAULT_SERVER_CLASS;
        }
        try {
            Class<?> serverClass = ReflectionUtil.getTypedClass(serverClassString);
            Constructor<?> constructor = serverClass.getDeclaredConstructor();
            iServer = (IServer) constructor.newInstance();
            iServer.prepare(dependencyManager);
        } catch (ClassNotFoundException |
                 InvocationTargetException |
                 NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException e) {
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

    @Override
    public IResource getResource() {
        return iServer;
    }
}
