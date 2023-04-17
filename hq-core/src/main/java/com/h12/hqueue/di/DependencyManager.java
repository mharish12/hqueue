package com.h12.hqueue.di;

import com.h12.hqueue.server.AppContext;

import java.io.IOException;

public class DependencyManager {

    private AppContext appContext;

    public DependencyManager() throws IOException {
        this(new AppContext());
    }

    public DependencyManager(AppContext appContext) {
        this.appContext = appContext;
    }

    public void start() {
        System.out.println("Server port: " + appContext.getEnvironment().getProperty("server.port"));
    }
}
