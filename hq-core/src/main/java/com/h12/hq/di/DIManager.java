package com.h12.hq.di;

import com.h12.hq.AbstractManager;
import com.h12.hq.DependencyManager;
import com.h12.hq.exception.HQException;
import com.h12.hq.util.Constants;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphException;
import io.github.classgraph.ScanResult;

public abstract class DIManager extends AbstractManager {

    protected DIManager() {}

    @Override
    public void prepare(DependencyManager dependencyManager) {
        scanPackages(dependencyManager);
    }

    @Override
    public void start() {

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
