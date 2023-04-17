package com.h12.hqueue.server.http;

import com.h12.hqueue.util.Constants;
import java.util.Properties;


public abstract class AbstractIServer implements IServer {
    public int getPort(Properties properties) {
        return Integer.parseInt(properties.getProperty(Constants.SERVER_PORT, Constants.DEFAULT_SERVER_PORT));
    }

    public String getHost(Properties properties) {
        return properties.getProperty(Constants.SERVER_HOST, Constants.DEFAULT_SERVER_HOST);
    }
}
