package com.h12.hq.server.http;

import com.h12.hq.AppContext;

public interface IServer {
    void prepare(AppContext appContext);
    void start();
    void stop();
}
