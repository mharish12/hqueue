package com.h12.hq.hooks;

public interface IShutDownHook extends Runnable {
    default void run() {
        shutdown();
    }

    void shutdown();
}
