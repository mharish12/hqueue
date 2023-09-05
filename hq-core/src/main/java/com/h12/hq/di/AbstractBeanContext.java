package com.h12.hq.di;

import com.h12.hq.AbstractContext;
import com.h12.hq.DependencyManager;
import com.h12.hq.di.annotation.*;
import com.h12.hq.exception.HQException;
import com.h12.hq.util.BeanUtils;
import com.h12.hq.util.Config;
import com.h12.hq.util.ReflectionUtil;
import com.h12.hq.util.StringConstants;
import io.github.classgraph.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
//            superClassInjection(classInfo, configurationClassBean);
            setFields(classInfo, configurationClassBean);
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
                    setFields(dependencyManager.getAppContext().getScanResult().getClassInfo(name), returnedBean);
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
        Object actualPropertyValue = null;
        if (propertyValue.contains(StringConstants.COMMA)) {
            actualPropertyValue = parseArrayOrCollectionValueType(fieldInfo, propertyValue);
        } else {
            actualPropertyValue = parseSingleValueType(fieldInfo, propertyValue);
        }
        if (actualPropertyValue != null) {
            field.setAccessible(true);
            field.set(classObject, actualPropertyValue);
            field.setAccessible(false);
        }
    }

    private Object parseSingleValueType(FieldInfo fieldInfo, String propertyValue) {
        Class<?> fieldType = fieldInfo.loadClassAndGetField().getType();
        if (fieldType == String.class) {
            return propertyValue;
        } else if ((fieldType == Integer.TYPE && fieldType.isPrimitive()) || fieldType == Integer.class) {
            return Integer.parseInt(propertyValue);
        } else if ((fieldType == Long.TYPE && fieldType.isPrimitive()) || fieldType == Long.class) {
            return Long.parseLong(propertyValue);
        } else if ((fieldType == Double.TYPE && fieldType.isPrimitive()) || fieldType == Double.class) {
            return Double.parseDouble(propertyValue);
        } else {
            return null;
        }
    }

    private Object parseArrayOrCollectionValueType(FieldInfo fieldInfo, String propertyValue) {
        Class<?> fieldType = fieldInfo.loadClassAndGetField().getType();
        if (fieldType.isArray() && fieldType == String[].class) {
            return propertyValue.split(StringConstants.COMMA);
        } else if (fieldType.isArray() && fieldType == Integer[].class) {
            String[] props = propertyValue.split(StringConstants.COMMA);
            Integer[] integers = new Integer[props.length];
            for (int i = 0; i < props.length; i++) {
                integers[i] = Integer.parseInt(props[i]);
            }
            return integers;
        } else if (fieldType.isArray() && fieldType == Long[].class) {
            String[] props = propertyValue.split(StringConstants.COMMA);
            Long[] longs = new Long[props.length];
            for (int i = 0; i < props.length; i++) {
                longs[i] = Long.parseLong(props[i]);
            }
            return longs;
        } else if (fieldType.isArray() && fieldType == Double[].class) {
            String[] props = propertyValue.split(StringConstants.COMMA);
            Double[] doubles = new Double[props.length];
            for (int i = 0; i < props.length; i++) {
                doubles[i] = Double.parseDouble(props[i]);
            }
            return doubles;
        }
        return parseCollectionValueType(fieldInfo, propertyValue);
    }

    private Object parseCollectionValueType(FieldInfo fieldInfo, String propertyValue) {
        Class<?> fieldType = fieldInfo.loadClassAndGetField().getType();
        if (fieldType == List.class) {
            List<String> list = new ArrayList<>();
            Collections.addAll(list, propertyValue.split(StringConstants.COMMA));
            return list;
        } else if (fieldType == Integer[].class) {
            String[] props = propertyValue.split(StringConstants.COMMA);
            Integer[] integers = new Integer[props.length];
            for (int i = 0; i < props.length; i++) {
                integers[i] = Integer.parseInt(props[i]);
            }
            return integers;
        } else if (fieldType == Long[].class) {
            String[] props = propertyValue.split(StringConstants.COMMA);
            Long[] longs = new Long[props.length];
            for (int i = 0; i < props.length; i++) {
                longs[i] = Long.parseLong(props[i]);
            }
            return longs;
        } else if (fieldType == Double[].class) {
            String[] props = propertyValue.split(StringConstants.COMMA);
            Double[] doubles = new Double[props.length];
            for (int i = 0; i < props.length; i++) {
                doubles[i] = Double.parseDouble(props[i]);
            }
            return doubles;
        }
        return null;
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
