package com.h12.hq.di;

import com.h12.hq.AppContext;
import com.h12.hq.di.annotation.*;
import com.h12.hq.util.Constants;
import com.h12.hq.util.ReflectionUtil;
import io.github.classgraph.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DIBeanManager extends DIManager {
    private AppContext appContext;
    public DIBeanManager() {
        super();
    }

    public void prepare(DependencyManager dependencyManager) {
        this.appContext = dependencyManager.getAppContext();
    }

    @Override
    public void start() {
        startDI();
    }

    private void startDI() {
        try {
            ClassInfoList autoWireClassInfoList = appContext.getScanResult().getClassesWithAnnotation(AutoWire.class);
            ClassInfoList controllerClassInfoList = appContext.getScanResult().getClassesWithAnnotation(Controller.class);
            ClassInfoList componentClassInfoList = appContext.getScanResult().getClassesWithAnnotation(Component.class);
            createPrimaryBeans();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createPrimaryBeans() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        ClassInfoList configurationAnnotationClassInfoList = appContext.getScanResult().getClassesWithAnnotation(Configuration.class);
//        ClassInfoList beanAnnotationClassInfoList = scanResult.getClassesWithMethodAnnotation(Bean.class);
//        ClassInfoList beanAndConfigurationUnionClassInfoList = beanAnnotationClassInfoList.filter(classInfo -> classInfo.hasAnnotation(Configuration.class));
        for (ClassInfo classInfo : configurationAnnotationClassInfoList) {
            Object configurationClassBean;
            MethodInfoList constructorMethodInfo = classInfo.getDeclaredConstructorInfo();
            MethodInfo defaultConstructorMethodInfo = constructorMethodInfo.get(0);
            Constructor<?> constructor = defaultConstructorMethodInfo.loadClassAndGetConstructor();
            configurationClassBean = ReflectionUtil.newInstance(constructor);
            setFields(classInfo, configurationClassBean); //TODO: set super class fields first.
            for(MethodInfo methodInfo : classInfo.getDeclaredMethodInfo()) {
                if (methodInfo.hasAnnotation(Bean.class)) {
                    Method method = methodInfo.loadClassAndGetMethod();
                    AnnotationInfo annotationInfo = methodInfo.getAnnotationInfo(Bean.class);
                    Bean beanAnnotation = (Bean) annotationInfo.loadClassAndInstantiate();
                    String name = beanAnnotation.qualifier();
                    if(name.equals(Constants.DEFAULT_BEAN_NAME)) {
                        name = method.getReturnType().getName();
                    }//TODO: multiple bean check
                    Object returnedBean = method.invoke(configurationClassBean);
                    appContext.getBeanFactory().put(name, returnedBean);
                }
            }
        }
    }

    private void setFields(ClassInfo classInfo, Object classObject) throws IllegalAccessException {
        FieldInfoList fieldInfoList =  classInfo.getDeclaredFieldInfo();
        for (FieldInfo fieldInfo: fieldInfoList) {
            Field field = fieldInfo.loadClassAndGetField();
            AnnotationInfoList annotationInfoList = fieldInfo.getAnnotationInfo();
            for(AnnotationInfo annotationInfo: annotationInfoList) {
                Annotation annotation = annotationInfo.loadClassAndInstantiate();
                if(annotation instanceof AutoWire autoWire) {
                    String beanName = autoWire.qualifier();
                    if(beanName.equals(Constants.DEFAULT_BEAN_NAME)) {
                        beanName = fieldInfo.getName();
                    }
                    Object injectableFieldObject = appContext.getBeanFactory().getBean(beanName);
                    field.setAccessible(true);
                    field.set(classObject, injectableFieldObject);
                    field.setAccessible(false);
                } else if (annotation instanceof Value value) {
                    String propertyName = value.value();
                    String propertyValue = appContext.getEnvironment().getProperty(propertyName);
                    if(propertyValue == null) {
                        propertyValue = value.defaultValue();
                    }
                    field.setAccessible(true);
                    field.set(classObject, propertyValue);
                    field.setAccessible(false);
                }
            }
        }
    }


    @Override
    public void stop() {

    }
}
