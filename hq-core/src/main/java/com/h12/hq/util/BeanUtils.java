package com.h12.hq.util;

import com.h12.hq.DependencyManager;
import com.h12.hq.exception.HQException;

import java.lang.reflect.InvocationTargetException;

public class BeanUtils {
    public static Object newAndUpdateFactory(DependencyManager dependencyManager, Class<?> clazz) {
        try {
            Object o = ReflectionUtil.newInstanceIfNotInterface(clazz);
            return updateFactory(clazz, o, dependencyManager);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new HQException(e);
        }
    }

    public static Object newAndUpdateFactoryIfNotExists(DependencyManager dependencyManager, Class<?> clazz) {
        if (dependencyManager.getAppContext().getBeanFactory().hasBean(clazz)) {
            return dependencyManager.getAppContext().getBeanFactory().getBean(clazz);
        } else {
            return newAndUpdateFactory(dependencyManager, clazz);
        }
    }

    public static Object updateFactory(Class<?> clazz, Object classObject, DependencyManager dependencyManager) {
        return dependencyManager.getAppContext().getBeanFactory().put(clazz, classObject);
    }
}
