package com.h12.hq.di;

import com.h12.hq.IManager;
import com.h12.hq.exception.HQException;
import com.h12.hq.util.Constants;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphException;
import io.github.classgraph.ScanResult;

public abstract class DIManager implements IManager {

    protected DIManager() {}

    @Override
    public void prepare(DependencyManager dependencyManager) {
        scanPackages(dependencyManager);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    protected void scanPackages(DependencyManager dependencyManager) {
        String packageToScan = dependencyManager.getAppContext().getEnvironment().getProperty(Constants.PACKAGE_TO_SCAN);
        ClassGraph classGraph = new ClassGraph();
        classGraph
                .enableAllInfo()
                .acceptPackages(packageToScan);
        try(ScanResult scanResult = classGraph.scan()) {
            dependencyManager.setScanResult(scanResult);
        } catch (ClassGraphException ex) {
            throw new HQException(ex.getMessage(), ex);
        }
    }
}
