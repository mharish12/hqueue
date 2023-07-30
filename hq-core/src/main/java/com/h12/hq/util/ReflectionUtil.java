package com.h12.hq.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionUtil {

    /**
     * New instance of the class provided.
     *
     * @param clazz java class.
     * @return new Instance of the class.
     */
    public static <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return newInstance(clazz.getDeclaredConstructor());
    }

    public static <T> T newInstance(Constructor<T> constructor, Object ...args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return constructor.newInstance(args);
    }

    public static Class<?> getTypedClass(String fqcn) throws ClassNotFoundException {
        return Class.forName(fqcn);
    }
}
