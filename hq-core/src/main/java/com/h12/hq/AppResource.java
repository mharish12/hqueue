package com.h12.hq;

import com.h12.hq.di.BeanFactory;
import io.github.classgraph.ScanResult;

import java.io.Serializable;

public class AppResource extends AbstractResource implements Serializable {
    private static BeanFactory factory;
    private static ScanResult scanResult;

    public BeanFactory getFactory() {
        return factory;
    }

    public void setFactory(BeanFactory factory) {
        AppResource.factory = factory;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        AppResource.scanResult = scanResult;
    }
}
