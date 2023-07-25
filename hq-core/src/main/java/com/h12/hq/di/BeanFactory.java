package com.h12.hq.di;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private static Map<String, Object> beans;

    public BeanFactory() {
        beans = new HashMap<>();
    }

    public <T> T getBean(Class<T> typeClass) {
        return (T) getBean(typeClass.getName());
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
}
