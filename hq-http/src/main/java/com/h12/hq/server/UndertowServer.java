package com.h12.hq.server;

import com.h12.hq.AppContext;
import com.h12.hq.DependencyManager;
import com.h12.hq.di.annotation.Controller;
import com.h12.hq.server.http.AbstractIServer;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;

public class UndertowServer extends AbstractIServer {
    private Undertow undertowServer;
    private Undertow.Builder builder;
    private AppContext appContext;
    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.appContext = dependencyManager.getAppContext();
        int port = super.getPort(appContext);
        String host = super.getHost(appContext);

//        Undertow.ListenerBuilder listenerBuilder = new Undertow.ListenerBuilder();
        builder = Undertow.builder()
                .addHttpListener(port, host);
        builder.addHttpListener(getPort(), getHost(), registerHandler(dependencyManager));
    }

    private HttpHandler registerHandler(DependencyManager dependencyManager) {
        PathHandler handler = new PathHandler(10);
        dependencyManager.getScanResult().getClassesWithAnnotation(Controller.class);
//        handler.
        return handler;
    }

    @Override
    public void start() {
        undertowServer = builder.build();
        undertowServer.start();
    }

    @Override
    public void stop() {
        undertowServer.stop();
    }

    protected Undertow.Builder getBuilder() {
        return this.builder;
    }

    @Override
    public void shutdown() {
        if(undertowServer != null) {
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
