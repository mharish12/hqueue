package com.h12.hq.server.http.impl;

import com.h12.hq.AbstractManager;
import com.h12.hq.DependencyManager;

public class RouteManager extends AbstractManager {
    private DependencyManager dependencyManager;

    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
    }

    @Override
    public void start() {

    }
}
