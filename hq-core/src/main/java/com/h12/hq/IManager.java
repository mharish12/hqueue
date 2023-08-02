package com.h12.hq;

public interface IManager {
    void prepare(DependencyManager dependencyManager);
    void start();
    void stop();
    IContext getContext();
}
