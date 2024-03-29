package com.h12.hq;

import com.h12.hq.hooks.ShutDownHookManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HQContext {
    private static final Logger logger = LoggerFactory.getLogger(HQContext.class);
    private static AppContext appContext;
    private static DependencyManager dependencyManager;
    private static ShutDownHookManager shutDownHookManager;

    protected HQContext() throws IOException {
        init();
    }

    private void init() throws IOException {
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
        HQContext.appContext = new AppContext();
        HQContext.dependencyManager = new DependencyManager(appContext);
        HQContext.shutDownHookManager = new ShutDownHookManager();
        appContext.prepare(dependencyManager);
        dependencyManager.prepare();
        shutDownHookManager.prepare(dependencyManager);
    }

    void start() {
        appContext.start();
        dependencyManager.start();
        shutDownHookManager.start();

//        try {
//            Thread.sleep(5000); // Simulating some work being done by the application
//        } catch (InterruptedException e) {
//            logger.info(e.getMessage(), e);
//        }
        logger.info("Application started.");
    }

    void stop() {
        appContext.stop();
        dependencyManager.stop();
        shutDownHookManager.stop();
    }

    protected AppContext getAppContext() {
        return appContext;
    }

    protected DependencyManager getDependencyManager() {
        return dependencyManager;
    }
}
