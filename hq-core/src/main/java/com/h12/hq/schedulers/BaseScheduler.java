package com.h12.hq.schedulers;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class BaseScheduler implements Scheduler {
    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    protected BaseScheduler() {
        this(new ScheduledThreadPoolExecutor(1));
    }

    public BaseScheduler(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        this.scheduledThreadPoolExecutor = scheduledThreadPoolExecutor;
    }

    public void schedule() {
        scheduledThreadPoolExecutor.schedule(this::execute, 10, TimeUnit.SECONDS);
    }

    public void schedule(long delay, TimeUnit timeUnit) {
        scheduledThreadPoolExecutor.schedule(this::execute, delay, timeUnit);
    }
}
