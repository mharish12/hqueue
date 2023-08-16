package com.h12.hq.server.http.handler;

import com.h12.hq.DependencyManager;
import com.h12.hq.server.http.AbstractHandler;
import io.prometheus.client.exporter.common.TextFormat;
import io.undertow.server.HttpServerExchange;

import java.io.StringWriter;
import java.io.Writer;

public class HttpMetricsHandler extends AbstractHandler implements io.undertow.server.HttpHandler {
    private final DependencyManager dependencyManager;

    public HttpMetricsHandler(DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Writer writer = new StringWriter();
        TextFormat.write004(writer, dependencyManager.getAppContext().getCollectorRegistry().metricFamilySamples());
        exchange.setStatusCode(200).getResponseSender().send(writer.toString());
    }
}
