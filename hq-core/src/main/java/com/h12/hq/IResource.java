package com.h12.hq;


public interface IResource {
    void prepare(DependencyManager dependencyManager);
    void start();
    void stop();
}
