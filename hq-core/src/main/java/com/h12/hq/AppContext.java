package com.h12.hq;

import com.h12.hq.di.BeanFactory;
import com.h12.hq.di.DependencyManager;
import io.github.classgraph.ScanResult;

import java.io.IOException;

public class AppContext implements IContext {
    private final Environment environment;
    private final BeanFactory beanFactory;
    private ScanResult scanResult;

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

    @Override
    public IResource getResource() {
        return null;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }
}
