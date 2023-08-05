package com.h12.hq.server.http.handler;

import com.h12.hq.DependencyManager;
import com.h12.hq.server.http.AbstractHandler;
import io.undertow.server.HttpServerExchange;

import java.lang.reflect.Method;
import java.util.Deque;
import java.util.Map;

public class RouteHttpHandler extends AbstractHandler implements io.undertow.server.HttpHandler {

    private final String route;
    private final DependencyManager dependencyManager;
    private final Method method;

    public RouteHttpHandler(String route, DependencyManager dependencyManager) {
        this(route, null, dependencyManager);
    }

    public RouteHttpHandler(String route, Method method, DependencyManager dependencyManager) {
        this.route = route;
        this.dependencyManager = dependencyManager;
        this.method = method;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Map<String, Deque<String>> pathParameters =  exchange.getPathParameters();
        Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();

    }
}
