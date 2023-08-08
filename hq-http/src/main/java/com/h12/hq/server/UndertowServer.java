package com.h12.hq.server;

import com.h12.hq.AppContext;
import com.h12.hq.DependencyManager;
import com.h12.hq.server.http.AbstractServer;
import com.h12.hq.server.http.handler.RouteHttpHandler;
import io.github.classgraph.MethodInfo;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UndertowServer extends AbstractServer {
    private static final Logger logger = LoggerFactory.getLogger(UndertowServer.class);
    private DependencyManager dependencyManager;
    private Undertow undertowServer;
    private AppContext appContext;

    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
        this.appContext = dependencyManager.getAppContext();

//        Undertow.ListenerBuilder listenerBuilder = new Undertow.ListenerBuilder();

    }

    private HttpHandler registerHandler(DependencyManager dependencyManager) {
        PathHandler handler = new PathHandler(10);
        for (Map.Entry<String, MethodInfo> route : dependencyManager.getAppContext().getRoutes().entrySet()) {
            HttpHandler httpHandler = new RouteHttpHandler(route.getKey(), route.getValue(), dependencyManager);
            handler.addPrefixPath(route.getKey(), httpHandler);
//            handler.addExactPath(route.getKey(), httpHandler);
        }
//        handler.
        return handler;
    }

    @Override
    public synchronized void start() {
        int port = super.getPort(appContext);
        String host = super.getHost(appContext);
        Undertow.Builder builder = Undertow.builder();
        builder.addHttpListener(port, host, registerHandler(dependencyManager));
        undertowServer = builder.build();
        undertowServer.start();
        logger.info("Server started.");
    }

    @Override
    public void stop() {
        if (undertowServer != null) {
            undertowServer.stop();
            undertowServer = null;
        }
    }

    @Override
    public void shutdown() {
        this.stop();
        if (undertowServer != null) {
            undertowServer.stop();
            undertowServer = null;
        }
    }

    @Override
    public String getHost() {
        return getHost(appContext);
    }

    @Override
    public Integer getPort() {
        return getPort(appContext);
    }
}
