package com.h12.hq;

import com.h12.hq.di.BeanFactory;
import com.h12.hq.di.DependencyManager;

import java.io.IOException;

public class AppContext implements IContext {
    private final Environment environment;
    private final BeanFactory beanFactory;

    public AppContext() throws IOException {
        this(new Environment(), new BeanFactory());
    }

    public AppContext(Environment environment, BeanFactory beanFactory) {
        this.environment = environment;
        this.beanFactory = beanFactory;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void prepare(DependencyManager dependencyManager) {

    }

    @Override
    public void start() {
        //No implementation.

    }

    @Override
    public void stop() {

    }
}
