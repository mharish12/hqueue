package com.h12.server;

import com.h12.hqueue.server.http.AbstractIServer;
import io.undertow.Undertow;

import java.util.Properties;

public class UndertowServer extends AbstractIServer {
    private Undertow undertowServer;
    private Undertow.Builder builder;
    @Override
    public void prepare(Properties properties) {
        int port = super.getPort(properties);
        String host = super.getHost(properties);
        builder = Undertow.builder()
                .addHttpListener(port, host);
    }

    @Override
    public void start() {
        undertowServer = builder.build();
        undertowServer.stop();
    }

    @Override
    public void stop() {
        undertowServer.stop();
    }

    protected Undertow.Builder getBuilder() {
        return this.builder;
    }
}
