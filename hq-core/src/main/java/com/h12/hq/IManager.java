package com.h12.hq;

import com.h12.hq.server.AppContext;

public interface IManager {
    void prepare(AppContext appContext);
    void start();
    void stop();
}
