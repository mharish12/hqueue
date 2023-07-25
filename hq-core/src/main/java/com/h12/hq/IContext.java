package com.h12.hq;

import com.h12.hq.di.DependencyManager;

import java.io.Serializable;

public interface IContext extends Serializable {
    void prepare(DependencyManager dependencyManager);
    void start();
    void stop();
}
