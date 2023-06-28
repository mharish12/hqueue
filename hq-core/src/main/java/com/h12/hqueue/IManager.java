package com.h12.hqueue;

import com.h12.hqueue.server.AppContext;

public interface IManager {
    void prepare(AppContext appContext);
    void start();
    void stop();
}
