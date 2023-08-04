package com.h12.hq.di;

import com.h12.hq.AbstractResource;
import com.h12.hq.DependencyManager;
import com.h12.hq.IResource;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory extends AbstractResource {
    private static Map<String, Object> beans;

    public BeanFactory() {
        beans = new HashMap<>();
    }

    public <T> T getBean(Class<T> typeClass) {
        Object o = getBean(typeClass.getName());
        return (T) o;
    }

    public Object getBean(String className) {
        return beans.get(className);
    }
    
    public Object put(Object object) {
        return put(object.getClass().getName(), object);
    }

    public Object put(String name, Object object) {
        return beans.put(name, object);
    }

    public Object put(Class<?> clazz, Object object) {
        return put(clazz.getName(), object);
    }

    public boolean hasBean(Class<?> clazz) {
        return beans.containsKey(clazz.getName());
    }
}
