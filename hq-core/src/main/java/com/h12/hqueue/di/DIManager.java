package com.h12.hqueue.di;

import com.h12.hqueue.IManager;
import com.h12.hqueue.server.AppContext;
import com.h12.hqueue.util.Constants;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

public class DIManager implements IManager {
    private static final long serialVersionUID = 123456L;
    private AppContext appContext;
    private ScanResult scanResult;

    @Override
    public void prepare(AppContext appContext) {
        this.appContext = appContext;
        String packageToScan = appContext.getEnvironment().getProperty(Constants.PACKAGE_TO_SCAN);
        ClassGraph classGraph = new ClassGraph();
        classGraph
                .enableAllInfo()
                .acceptPackages(packageToScan);
        this.scanResult = classGraph.scan();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        // No implementation
    }
}
