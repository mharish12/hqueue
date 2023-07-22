package com.h12.hq.di.impl;

import com.h12.hq.di.DIManager;
import com.h12.hq.di.annotation.*;
import com.h12.hq.util.Constants;
import com.h12.hq.util.ReflectionUtil;
import io.github.classgraph.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class DIManagerImpl extends DIManager {

    @Override
    public void start() {
        startDI();
    }

    private void startDI() {
        try {
            ClassInfoList autoWireClassInfoList = scanResult.getClassesWithAnnotation(AutoWire.class);
            ClassInfoList controllerClassInfoList = scanResult.getClassesWithAnnotation(Controller.class);
            ClassInfoList componentClassInfoList = scanResult.getClassesWithAnnotation(Component.class);
            createPrimaryBeans();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createPrimaryBeans() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        ClassInfoList configurationAnnotationClassInfoList = scanResult.getClassesWithAnnotation(Configuration.class);
//        ClassInfoList beanAnnotationClassInfoList = scanResult.getClassesWithMethodAnnotation(Bean.class);
//        ClassInfoList beanAndConfigurationUnionClassInfoList = beanAnnotationClassInfoList.filter(classInfo -> classInfo.hasAnnotation(Configuration.class));
        for (ClassInfo classInfo : configurationAnnotationClassInfoList) {
            Object configurationClassBean;
            MethodInfoList constructorMethodInfo = classInfo.getDeclaredConstructorInfo();
            MethodInfo defaultConstructorMethodInfo = constructorMethodInfo.get(0);
            Constructor<?> constructor = defaultConstructorMethodInfo.loadClassAndGetConstructor();
            configurationClassBean = ReflectionUtil.newInstance(constructor);
            for(MethodInfo methodInfo : classInfo.getDeclaredMethodInfo()) {
                if (methodInfo.hasAnnotation(Bean.class)) {
                    Method method = methodInfo.loadClassAndGetMethod();
                    AnnotationInfo annotationInfo = methodInfo.getAnnotationInfo(Bean.class);
                    Bean beanAnnotation = (Bean) annotationInfo.loadClassAndInstantiate();
                    String name = beanAnnotation.qualifier();
                    if(name.equals(Constants.DEFAULT_BEAN_NAME)) {
                        name = method.getReturnType().getName();
                    }

                }
            }
        }
    }

    @Override
    public void stop() {

    }
}
