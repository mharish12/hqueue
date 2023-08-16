package com.h12.hq.di;

import com.h12.hq.DependencyManager;
import com.h12.hq.di.annotation.Controller;
import com.h12.hq.util.BeanUtils;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;

public class DIBeanContext extends AbstractBeanContext {
    public DIBeanContext() {
        super();
    }

    public void prepare(DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
    }

    @Override
    public void start() {
        startDI();
    }

    private void startDI() {
        try {
            createPrimaryBeans();
            startControllerDI();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void startControllerDI() throws IllegalAccessException {
        ClassInfoList controllerClassInfoList = dependencyManager.getAppContext().getScanResult().getClassesWithAnnotation(Controller.class);
        for (ClassInfo classInfo : controllerClassInfoList) {
            Object classObject = BeanUtils.getOrNewAndUpdateFactory(dependencyManager, classInfo.loadClass());
            iteratorForFieldsAnnotationAndInject(classInfo, classObject);
        }
    }

}
