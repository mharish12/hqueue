package com.h12.hq.server.http.handler;

import com.h12.hq.DependencyManager;
import com.h12.hq.server.http.AbstractHandler;
import com.h12.hq.util.JsonUtils;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.MethodInfo;
import io.undertow.server.HttpServerExchange;

import java.util.Deque;
import java.util.Map;

public class RouteHttpHandler extends AbstractHandler implements io.undertow.server.HttpHandler {

    private final String route;
    private final DependencyManager dependencyManager;
    private final MethodInfo methodInfo;

    public RouteHttpHandler(String route, DependencyManager dependencyManager) {
        this(route, null, dependencyManager);
    }

    public RouteHttpHandler(String route, MethodInfo methodInfo, DependencyManager dependencyManager) {
        this.route = route;
        this.dependencyManager = dependencyManager;
        this.methodInfo = methodInfo;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Map<String, Deque<String>> pathParameters = exchange.getPathParameters();
        Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();
        ClassInfo classInfo = methodInfo.getClassInfo();
        Object controllerClassObject = dependencyManager.getAppContext().getBeanFactory().getBean(classInfo.getName());
        Object returnValue = methodInfo.loadClassAndGetMethod().invoke(controllerClassObject);
        exchange.setStatusCode(200).getResponseSender().send(JsonUtils.toJsonString(returnValue));
    }
}
