package com.h12.hqueue.server.http;

import java.util.Properties;

public interface IServer {
    void prepare(Properties properties);
    void start();
    void stop();
}
