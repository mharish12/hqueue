package com.h12.hq;

import java.io.Serializable;

public interface IContext extends Serializable {
    void prepare(DependencyManager dependencyManager);
    void start();
    void stop();
    IResource getResource();
}
