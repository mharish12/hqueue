package com.h12.hq;

import com.h12.hq.di.DependencyManager;

public interface IManager {
    void prepare(DependencyManager dependencyManager);
    void start();
    void stop();
}
