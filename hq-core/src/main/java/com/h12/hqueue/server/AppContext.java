package com.h12.hqueue.server;

import java.io.IOException;

public class AppContext {
    private Environment environment;

    public AppContext() throws IOException {
        this(new Environment());
    }

    public AppContext(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return this.environment;
    }
}
