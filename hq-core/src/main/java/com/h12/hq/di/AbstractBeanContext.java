package com.h12.hq.di;

import com.h12.hq.AbstractContext;
import com.h12.hq.DependencyManager;
import com.h12.hq.di.annotation.*;
import com.h12.hq.exception.HQException;
import com.h12.hq.util.BeanUtils;
import com.h12.hq.util.Config;
import com.h12.hq.util.ReflectionUtil;
import io.github.classgraph.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

abstract class AbstractBeanContext extends AbstractContext {
    protected DependencyManager dependencyManager;

    AbstractBeanContext() {
    }

    public void prepare(DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
    }

    void createPrimaryBeans() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        ClassInfoList configurationAnnotationClassInfoList = dependencyManager.getAppContext().getScanResult().getClassesWithAnnotation(Configuration.class);
//        ClassInfoList beanAnnotationClassInfoList = scanResult.getClassesWithMethodAnnotation(Bean.class);
//        ClassInfoList beanAndConfigurationUnionClassInfoList = beanAnnotationClassInfoList.filter(classInfo -> classInfo.hasAnnotation(Configuration.class));
        for (ClassInfo classInfo : configurationAnnotationClassInfoList) {
            Object configurationClassBean;
            MethodInfoList constructorMethodInfo = classInfo.getDeclaredConstructorInfo();
            MethodInfo defaultConstructorMethodInfo = constructorMethodInfo.get(0);
            Constructor<?> constructor = defaultConstructorMethodInfo.loadClassAndGetConstructor();
            configurationClassBean = ReflectionUtil.newInstance(constructor);
            setFields(classInfo, configurationClassBean); //TODO: set super class fields first.
            for (MethodInfo methodInfo : classInfo.getDeclaredMethodInfo()) {
                if (methodInfo.hasAnnotation(Bean.class)) {
                    Method method = methodInfo.loadClassAndGetMethod();
                    AnnotationInfo annotationInfo = methodInfo.getAnnotationInfo(Bean.class);
                    Bean beanAnnotation = (Bean) annotationInfo.loadClassAndInstantiate();
                    String name = beanAnnotation.qualifier();
                    if (name.equals(Config.DEFAULT_BEAN_NAME)) {
                        name = method.getReturnType().getName();
                    }//TODO: check if multiple bean exists
                    Object returnedBean = method.invoke(configurationClassBean);
                    dependencyManager.getAppContext().getBeanFactory().put(name, returnedBean);
                }
            }
        }
    }
    void superClassInjection(ClassInfo childClass, Object classObject) throws IllegalAccessException {
        if (childClass != null && !childClass.isInterface() && !childClass.isAnnotation()) {
            ClassInfo supperClass = childClass.getSuperclass();
            if (supperClass != null
                    && !supperClass.isAnnotation()
                    && !supperClass.isInterface()) {
                superClassInjection(supperClass, classObject);
                setFields(supperClass, classObject);
            }
        }
    }

    void setFields(ClassInfo classInfo, Object classObject) throws IllegalAccessException {
        FieldInfoList fieldInfoList = classInfo.getDeclaredFieldInfo();
        for (FieldInfo fieldInfo : fieldInfoList) {
            AnnotationInfoList annotationInfoList = fieldInfo.getAnnotationInfo();
            for (AnnotationInfo annotationInfo : annotationInfoList) {
                Annotation annotation = annotationInfo.loadClassAndInstantiate();
                if (annotation instanceof AutoWire autoWire) {
                    injectAutoWireToField(fieldInfo, autoWire, classObject);
                } else if (annotation instanceof Value value) {
                    injectValueToField(fieldInfo, value, classObject);
                }
            }
        }
    }

    void injectAutoWireToField(FieldInfo fieldInfo, Annotation annotation, Object classObject) throws IllegalAccessException {
        AutoWire autoWire = (AutoWire) annotation;
        Field field = fieldInfo.loadClassAndGetField();
        Class<?> fieldType = field.getType();
        String beanName = autoWire.qualifier();
        if (beanName.equals(Config.DEFAULT_BEAN_NAME)) {
            beanName = fieldType.getName();
        }
        Object injectableFieldObject = dependencyManager.getAppContext().getBeanFactory().getBean(beanName);
        if (injectableFieldObject == null) {
            if (!fieldType.isInterface() && !fieldType.isAnonymousClass()) {
                ClassInfo thisFieldClassInfo = dependencyManager.getScanResult().getClassInfo(fieldType.getName());
                injectableFieldObject = autoWireWithNewObject(thisFieldClassInfo);
            }
        }
        field.setAccessible(true);
        field.set(classObject, injectableFieldObject);//TODO: check if bean does not exists.
        field.setAccessible(false);
    }

    void injectValueToField(FieldInfo fieldInfo, Annotation annotation, Object classObject) throws IllegalAccessException {
        Value value = (Value) annotation;
        Field field = fieldInfo.loadClassAndGetField();
        String propertyName = value.value();
        String propertyValue = dependencyManager.getAppContext().getEnvironment().getProperty(propertyName);
        if (propertyValue == null) {
            propertyValue = value.defaultValue();
        }
        field.setAccessible(true);
        field.set(classObject, propertyValue);
        field.setAccessible(false);
    }

    Object autoWireWithNewObject(ClassInfo classInfo) throws IllegalAccessException {
        Class<?> clazz = classInfo.loadClass();
        if (!classInfo.isInterface()) {
            Object o = BeanUtils.getOrNewAndUpdateFactory(dependencyManager, clazz);
            superClassInjection(classInfo, o);
            findAnnotationOnClassAndInjectFields(clazz, o);
            return o;
        } else {
            throw new HQException("Cannot instantiate interface: " + classInfo.getName());
        }
    }

    void findAnnotationOnClassAndInjectFields(Class<?> clazz, Object classObject) throws IllegalAccessException {
        ClassInfo classInfo = dependencyManager.getScanResult().getClassInfo(clazz.getName());
        boolean isService = classInfo.hasAnnotation(Service.class);
        boolean isComponent = classInfo.hasAnnotation(Component.class);
        if (isService) {
            iteratorForFieldsAnnotationAndInject(classInfo, classObject);
        } else if (isComponent) {
            iteratorForFieldsAnnotationAndInject(classInfo, classObject);
        }
    }

    void iteratorForFieldsAnnotationAndInject(ClassInfo classInfo, Object classObject) throws IllegalAccessException {
        for (FieldInfo fieldInfo : classInfo.getDeclaredFieldInfo()) {
            boolean hasAutoWireAnnotation = fieldInfo.hasAnnotation(AutoWire.class);
            boolean hasValueAnnotation = fieldInfo.hasAnnotation(Value.class);
            if (hasValueAnnotation && hasAutoWireAnnotation) {
                throw new HQException("A field cannot have both " + Value.class.getName() + " and " + AutoWire.class.getName() + " annotations.");
            } else if (hasValueAnnotation) {
                injectValueToField(fieldInfo, fieldInfo.getAnnotationInfo(Value.class).loadClassAndInstantiate(), classObject);
            } else if (hasAutoWireAnnotation) {
                injectAutoWireToField(fieldInfo, fieldInfo.getAnnotationInfo(AutoWire.class).loadClassAndInstantiate(), classObject);
            }
        }
    }
}