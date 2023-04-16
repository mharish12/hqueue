package com.h12.hqueue.di;

import com.h12.hqueue.server.AppContext;

public class DependencyManager {

    private AppContext appContext;

    public DependencyManager() {
        this(new AppContext());
    }

    public DependencyManager(AppContext appContext) {
        this.appContext = appContext;
    }

    public void start() {

    }
}
