package com.h12.hq.di;

import com.h12.hq.IManager;
import com.h12.hq.server.AppContext;
import com.h12.hq.util.Constants;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

public abstract class DIManager implements IManager {
    private static final long serialVersionUID = 123456L;
    protected AppContext appContext;
    protected ScanResult scanResult;

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

}
