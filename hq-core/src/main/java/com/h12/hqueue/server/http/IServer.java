package com.h12.hqueue.server.http;

import com.h12.hqueue.server.AppContext;

public interface IServer {
    void prepare(AppContext appContext);
    void start();
    void stop();
}
