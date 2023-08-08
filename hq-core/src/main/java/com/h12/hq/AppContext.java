package com.h12.hq;

import com.h12.hq.di.BeanFactory;
import io.github.classgraph.MethodInfo;
import io.github.classgraph.ScanResult;

import java.io.IOException;
import java.util.Map;

public class AppContext extends AbstractContext {
    private final Environment environment;
    private final BeanManager beanManager;
    private final AppResource appResource;

    public AppContext() throws IOException {
        this(new Environment(), new BeanManager());
    }

    public AppContext(Environment environment, BeanManager beanManager) {
        this.environment = environment;
        this.beanManager = beanManager;
        this.appResource = new AppResource();
    }

    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.beanManager.prepare(dependencyManager);
    }

    @Override
    public void start() {
        this.beanManager.start();
        this.appResource.setBeanFactory((BeanFactory) beanManager.getContext().getResource());
    }

    @Override
    public void stop() {
        this.beanManager.stop();
    }

    @Override
    public IResource getResource() {
        return appResource.getBeanFactory();
    }

    public Map<String, MethodInfo> getRoutes() {
        return appResource.getRoutes();
    }

    public void putRoute(String path, MethodInfo methodInfo) {
        appResource.putRoute(path, methodInfo);
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public BeanFactory getBeanFactory() {
        return appResource.getBeanFactory();
    }

    public ScanResult getScanResult() {
        return appResource.getScanResult();
    }

    public void setScanResult(ScanResult scanResult) {
        this.appResource.setScanResult(scanResult);
    }
}
