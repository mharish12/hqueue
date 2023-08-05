package com.h12.hq.hooks;

public abstract class ShutDownHook implements IShutDownHook {
    @Override
    public void run() {
        shutdown();
    }
}
