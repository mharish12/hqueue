package com.h12.hq;


import com.h12.hq.di.DependencyManager;

public interface IResource {
    void prepare(DependencyManager dependencyManager);
    void start();
    void stop();
}
