package com.h12.hq.hooks;

import com.h12.hq.AbstractManager;
import com.h12.hq.DependencyManager;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;

public class ShutDownHookManager extends AbstractManager {
    private DependencyManager dependencyManager;

    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
    }

    @Override
    public void start() {
        Runtime runtime = Runtime.getRuntime();
        ClassInfoList classInfoList = dependencyManager.getScanResult().getClassesImplementing(IShutDownHook.class);
        for (ClassInfo classInfo : classInfoList) {
            String hookName = classInfo.getName();
            IShutDownHook resource = (IShutDownHook) dependencyManager.getAppContext().getBeanFactory().getBean(hookName);
            runtime.addShutdownHook(new Thread(resource));
        }
//        runtime.gc();
    }
}
