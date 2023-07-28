package com.h12.hq.di;

import com.h12.hq.AppContext;
import com.h12.hq.IManager;
import com.h12.hq.IValidator;
import com.h12.hq.di.impl.HQValidator;
import com.h12.hq.exception.HQException;
import com.h12.hq.util.Constants;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphException;
import io.github.classgraph.ScanResult;

public abstract class DIManager implements IManager {
    private final IValidator validator;

    public DIManager() {
        this(new HQValidator());
    }
    public DIManager(IValidator validator) {
        this.validator = validator;
    }

    @Override
    public void prepare(DependencyManager dependencyManager) {
        scanPackages(dependencyManager.getAppContext());
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    protected void scanPackages(AppContext appContext) {
        String packageToScan = appContext.getEnvironment().getProperty(Constants.PACKAGE_TO_SCAN);
        ClassGraph classGraph = new ClassGraph();
        classGraph
                .enableAllInfo()
                .acceptPackages(packageToScan);
        try(ScanResult scanResult = classGraph.scan()) {
            appContext.setScanResult(scanResult);
        } catch (ClassGraphException ex) {
            throw new HQException(ex.getMessage(), ex);
        }
    }
}
