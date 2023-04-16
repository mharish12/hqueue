package com.h12.ecommerce.server;

import com.h12.hqueue.server.http.IServer;
import com.h12.hqueue.util.Constants;
import io.undertow.Undertow;

import java.util.Properties;

public class UndertowServer implements IServer {
    private Undertow undertowServer;
    private Undertow.Builder builder;
    @Override
    public void prepare(Properties properties) {
        int port = Integer.parseInt(properties.getProperty(Constants.SERVER_PORT, Constants.DEFAULT_SERVER_PORT));
        String host = properties.getProperty(Constants.SERVER_HOST, Constants.DEFAULT_SERVER_HOST);
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
}
