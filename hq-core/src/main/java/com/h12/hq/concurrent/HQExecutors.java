package com.h12.hq.concurrent;

import java.util.concurrent.*;

public class HQExecutors {
    //    private static Executor executor;
    private static ExecutorService executorService;

    static {
//        executor = new ScheduledThreadPoolExecutor(10);
        executorService = Executors.newFixedThreadPool(10);
    }

    public HQExecutors() {
        executorService = Executors.newFixedThreadPool(10);
    }

    public static Object submitAndWait(Runnable runnable) throws ExecutionException, InterruptedException {
        Future<?> future = executorService.submit(runnable);
        return future.get();
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
