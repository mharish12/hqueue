package com.h12.hq.server.http;

import com.h12.hq.DependencyManager;
import com.h12.hq.HQValidator;

import java.lang.reflect.Method;

public abstract class AbstractHandler extends HQValidator implements IResolver {

    public AbstractHandler() {
        super();
    }

    @Override
    public void resolve(Method method, Object methodClassObject, DependencyManager dependencyManager, Object... args) {
        //TODO: implement to get all the details required to resolve this method.

        validate(methodClassObject, method, args);
    }
}
