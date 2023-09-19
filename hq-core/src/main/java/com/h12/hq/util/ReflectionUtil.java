package com.h12.hq.util;

import io.github.classgraph.FieldInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class ReflectionUtil {

    /**
     * New instance of the class provided by calling default constructor.
     *
     * @param clazz java class.
     * @return new Instance of the class.
     */
    public static <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return newInstanceIfNotInterface(clazz);
    }

    public static <T> T newInstanceIfNotInterface(Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (tClass.isInterface()) {
            return null;
        }
        return newInstance(tClass.getDeclaredConstructor());
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return constructor.newInstance(args);
    }

    public static Class<?> getTypedClass(String fqcn) throws ClassNotFoundException {
        return Class.forName(fqcn);
    }

    public static boolean isCollectionOrArray(FieldInfo fieldInfo) {
        Class<?> fieldType = fieldInfo.loadClassAndGetField().getType();
        if (fieldType.isArray()) {
            return true;
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            return true;
        } else if (Map.class.isAssignableFrom(fieldType)) {
            return false;//TODO: Need to make map assignable form.
        }
        return false;
    }
}
