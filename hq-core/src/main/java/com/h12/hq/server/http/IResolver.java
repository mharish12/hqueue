package com.h12.hq.server.http;

import com.h12.hq.DependencyManager;
import com.h12.hq.IPrepare;

import java.lang.reflect.Method;

public interface IResolver {
    void resolve(Method method, Object methodClassObject, DependencyManager dependencyManager, Object... args);
}
