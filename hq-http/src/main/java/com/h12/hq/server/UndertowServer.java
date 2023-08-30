package com.h12.hq.server;

import com.h12.hq.AppContext;
import com.h12.hq.DependencyManager;
import com.h12.hq.server.http.AbstractServer;
import com.h12.hq.server.http.handler.HttpMetricsHandler;
import com.h12.hq.server.http.handler.RouteHttpHandler;
import io.github.classgraph.MethodInfo;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.handlers.AuthenticationCallHandler;
import io.undertow.security.handlers.AuthenticationMechanismsHandler;
import io.undertow.security.impl.BasicAuthenticationMechanism;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UndertowServer extends AbstractServer {
    private static final Logger logger = LoggerFactory.getLogger(UndertowServer.class);
    private DependencyManager dependencyManager;
    private Undertow undertowServer;
    private AppContext appContext;
    private PathHandler handler;

    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
        this.appContext = dependencyManager.getAppContext();
        handler = new PathHandler(10);

        // Create a basic authentication mechanism
        AuthenticationMechanism mechanism = new BasicAuthenticationMechanism("MyRealm");

// Wrap the mechanism with an authentication handler
        AuthenticationCallHandler authenticationHandler = new AuthenticationCallHandler(handler);
        List<AuthenticationMechanism> authenticationMechanisms = new ArrayList<>();
        authenticationMechanisms.add(mechanism);
// Use AuthenticationMechanismsHandler to handle multiple mechanisms
        AuthenticationMechanismsHandler authMechanismsHandler = new AuthenticationMechanismsHandler(authenticationMechanisms);
//        Undertow.ListenerBuilder listenerBuilder = new Undertow.ListenerBuilder();
//        RolesAllowedHandler rolesAllowedHandler = new RolesAllowedHandler(authMechanismsHandler, "admin");
    }

    private HttpHandler registerHandlers(DependencyManager dependencyManager) {

        handler.addExactPath("/metrics", new HttpMetricsHandler(dependencyManager));
        for (Map.Entry<String, MethodInfo> route : dependencyManager.getAppContext().getRoutes().entrySet()) {
            HttpHandler httpHandler = new RouteHttpHandler(route.getKey(), route.getValue(), dependencyManager);
            if(!route.getKey().equals("/metrics")) {
                handler.addPrefixPath(route.getKey(), httpHandler);
            }
//            handler.addExactPath(route.getKey(), httpHandler);
        }
//        handler.
        return handler;
    }

    @Override
    public synchronized void start() {
        int port = super.getPort(appContext);
        String host = super.getHost(appContext);
        Undertow.Builder builder = Undertow.builder()
                .setServerOption(UndertowOptions.ENABLE_STATISTICS, true)
                .setServerOption(UndertowOptions.ALWAYS_SET_DATE, true);
        builder.addHttpListener(port, host, registerHandlers(dependencyManager));
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
