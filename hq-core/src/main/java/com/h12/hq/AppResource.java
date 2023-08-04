package com.h12.hq;

import com.h12.hq.di.BeanFactory;
import io.github.classgraph.ScanResult;

import java.io.Serializable;

public class AppResource extends AbstractResource implements Serializable {
    private static BeanFactory beanFactory;
    private static ScanResult scanResult;

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
}
