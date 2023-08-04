package com.h12.hq.server.http.handler;

import com.h12.hq.DependencyManager;
import io.undertow.server.HttpServerExchange;

public class RouteHttpHandler implements io.undertow.server.HttpHandler {

    private final String route;
    private final DependencyManager dependencyManager;

    public RouteHttpHandler(String route, DependencyManager dependencyManager) {
        this.route = route;
        this.dependencyManager = dependencyManager;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

    }
}
