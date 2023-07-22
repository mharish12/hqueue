package com.h12.hq.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionUtil {
    public static <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return newInstance(clazz.getDeclaredConstructor());
    }

    public static <T> T newInstance(Constructor<T> constructor, Object ...args ) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return constructor.newInstance(args);
    }
}
