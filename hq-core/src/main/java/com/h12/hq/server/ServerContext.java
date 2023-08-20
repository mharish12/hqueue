package com.h12.hq.server;

import com.h12.hq.AbstractContext;
import com.h12.hq.DependencyManager;
import com.h12.hq.IResource;
import com.h12.hq.exception.ServerException;
import com.h12.hq.server.http.AbstractServer;
import com.h12.hq.util.Config;
import com.h12.hq.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ServerContext extends AbstractContext {
    private static final Logger logger = LoggerFactory.getLogger(ServerContext.class);
    private AbstractServer iServer;

    @Override
    public void prepare(DependencyManager dependencyManager) {
        String serverClassString = dependencyManager.getAppContext().getEnvironment().getProperty(Config.SERVER_CLASS);
        if (serverClassString == null) {
            serverClassString = Config.DEFAULT_SERVER_CLASS;
        }
        try {
            if (StringUtils.isNotEmpty(serverClassString)) {
                Class<?> serverClass = ReflectionUtil.getTypedClass(serverClassString);
                Constructor<?> constructor = serverClass.getDeclaredConstructor();
                iServer = (AbstractServer) constructor.newInstance();
                iServer.prepare(dependencyManager);
            } else {
                logger.info("Server not prepared.(Server class is empty)");
            }
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
        if (iServer != null) {
            iServer.start();
        }
    }

    @Override
    public void stop() {
        if (iServer != null) {
            iServer.stop();
        }
    }

    @Override
    public IResource getResource() {
        return iServer;
    }
}
