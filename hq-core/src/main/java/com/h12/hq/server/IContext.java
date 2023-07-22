package com.h12.hq.server;

import java.io.Serializable;

public interface IContext extends Serializable {
    void start();
    void stop();
}
