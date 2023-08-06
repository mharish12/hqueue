package com.h12.hq.hooks;

import com.h12.hq.AbstractManager;
import com.h12.hq.DependencyManager;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;

import java.util.HashSet;
import java.util.Set;

public class ShutDownHookManager extends AbstractManager {
    private DependencyManager dependencyManager;
    private static final Set<String> registeredHooks = new HashSet<>();

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
            if(!classInfo.isInterfaceOrAnnotation()
                    && !classInfo.isAbstract()
                    && !classInfo.isAnonymousInnerClass()
                    && classInfo.isPublic()) {
                if(!registeredHooks.contains(hookName)) {
                    IShutDownHook resource = (IShutDownHook) dependencyManager.getAppContext().getBeanFactory().getBean(hookName);
                    runtime.addShutdownHook(new Thread(resource));
                    registeredHooks.add(hookName);
                }
            }
        }
//        runtime.gc();
    }
}
