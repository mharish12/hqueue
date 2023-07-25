package com.h12.hq.di.impl;

import com.h12.hq.di.DIManager;
import com.h12.hq.di.annotation.*;
import com.h12.hq.AppContext;
import com.h12.hq.util.Constants;
import com.h12.hq.util.ReflectionUtil;
import io.github.classgraph.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DIManagerImpl extends DIManager {
    private AppContext appContext;
    private ScanResult scanResult;

    public DIManagerImpl() {
    }

    @Override
    public void prepare(AppContext appContext) {
        this.appContext = appContext;
        String packageToScan = appContext.getEnvironment().getProperty(Constants.PACKAGE_TO_SCAN);
        ClassGraph classGraph = new ClassGraph();
        classGraph
                .enableAllInfo()
                .addClassLoader(this.getClass().getClassLoader())
                .acceptPackages(packageToScan);
        this.scanResult = classGraph.scan();
    }

    @Override
    public void start() {
        startDI();
    }

    private void startDI() {
        try {
//            ClassInfoList autoWireClassInfoList = scanResult.getClassesWithAnnotation(AutoWire.class);
//            ClassInfoList controllerClassInfoList = scanResult.getClassesWithAnnotation(Controller.class);
//            ClassInfoList componentClassInfoList = scanResult.getClassesWithAnnotation(Component.class);
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
            setFields(classInfo, configurationClassBean);
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

    private void setFields(ClassInfo classInfo, Object classObject) {
       FieldInfoList fieldInfoList =  classInfo.getDeclaredFieldInfo();
        for (FieldInfo fieldInfo: fieldInfoList) {

        }
    }

    @Override
    public void stop() {

    }
}
