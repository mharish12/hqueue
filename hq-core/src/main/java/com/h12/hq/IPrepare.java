package com.h12.hq;

public interface IPrepare {
    void prepare(DependencyManager dependencyManager);

    void start();

    void stop();
}
