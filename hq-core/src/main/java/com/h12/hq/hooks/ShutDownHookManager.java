package com.h12.hq.hooks;

import com.h12.hq.AbstractManager;
import com.h12.hq.DependencyManager;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class ShutDownHookManager extends AbstractManager {
    private static final Logger log = LoggerFactory.getLogger(ShutDownHookManager.class);
    private DependencyManager dependencyManager;
    private static final Set<String> registeredHooks = new HashSet<>();
    private static final Runtime runtime;

    static {
        runtime = Runtime.getRuntime();
    }

    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
    }

    @Override
    public void start() {
        ClassInfoList classInfoList = dependencyManager.getScanResult().getClassesImplementing(IShutDownHook.class);
        for (ClassInfo classInfo : classInfoList) {
            String hookName = classInfo.getName();
            if (!classInfo.isInterfaceOrAnnotation()
                    && !classInfo.isAbstract()
                    && !classInfo.isAnonymousInnerClass()
                    && classInfo.isPublic()) {
                if (!registeredHooks.contains(hookName)) {
                    IShutDownHook resource = (IShutDownHook) dependencyManager.getAppContext().getBeanFactory().getBean(hookName);
                    runtime.addShutdownHook(new Thread(resource));
                    registeredHooks.add(hookName);
                    log.info("Registered Shutdown hook: {}", hookName);
                }
            }
        }
    }

    @Override
    public void stop() {
        log.info("shutting down gracefully.");
    }
}
