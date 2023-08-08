package com.h12.hq;

import com.h12.hq.di.BeanFactory;
import io.github.classgraph.MethodInfo;
import io.github.classgraph.ScanResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AppResource extends AbstractResource implements Serializable {
    private static BeanFactory beanFactory;
    private static ScanResult scanResult;
    private static Map<String, MethodInfo> routes;

    public AppResource() {
        routes = new HashMap<>();
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory factory) {
        AppResource.beanFactory = factory;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        AppResource.scanResult = scanResult;
    }

    public void putRoute(String path, MethodInfo methodInfo) {
        routes.put(path, methodInfo);
    }

    public Map<String, MethodInfo> getRoutes() {
        return routes;
    }
}
