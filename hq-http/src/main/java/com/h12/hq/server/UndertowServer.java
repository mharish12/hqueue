package com.h12.hq.server;

import com.h12.hq.server.AppContext;
import com.h12.hq.server.http.AbstractIServer;
import io.undertow.Undertow;

public class UndertowServer extends AbstractIServer {
    private Undertow undertowServer;
    private Undertow.Builder builder;
    @Override
    public void prepare(AppContext appContext) {
        int port = super.getPort(appContext);
        String host = super.getHost(appContext);
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

    @Override
    public void shutdown() {
        if(undertowServer != null) {
            undertowServer.stop();
            undertowServer = null;
        }
    }
}
