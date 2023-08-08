package com.h12.hq.server.http;

import com.h12.hq.AbstractManager;
import com.h12.hq.AppResource;
import com.h12.hq.DependencyManager;
import com.h12.hq.di.annotation.Controller;
import com.h12.hq.util.StringConstants;
import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.MethodInfo;

import javax.ws.rs.Path;

public class RouteManager extends AbstractManager {
    private DependencyManager dependencyManager;

    @Override
    public void prepare(DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
    }

    @Override
    public void start() {
        ClassInfoList controllerClasses = this.dependencyManager.getScanResult().getClassesWithAnnotation(Controller.class);
        for(ClassInfo controllerClass: controllerClasses) {
            String basePath = StringConstants.EMPTY_STRING;
            if(controllerClass.hasAnnotation(Path.class)) {
                AnnotationInfo annotationInfo = controllerClass.getAnnotationInfo(Path.class);
                Path path = (Path) annotationInfo.loadClassAndInstantiate();
                basePath = formulate(path.value());
            }
            for(MethodInfo methodInfo: controllerClass.getDeclaredMethodInfo()) {
                String methodPath = "";
                if(methodInfo.hasAnnotation(Path.class)) {
                    AnnotationInfo annotationInfo = methodInfo.getAnnotationInfo(Path.class);
                    Path methodAnnotationPath = (Path) annotationInfo.loadClassAndInstantiate();
                    methodPath = formulate(methodAnnotationPath.value());
                }
                String fullRoute = basePath + methodPath;
                dependencyManager.getAppContext().putRoute(fullRoute, methodInfo);
            }
        }
    }

    public String formulate(String path) {
        StringBuilder pathBuilder = new StringBuilder();
        for(String route: path.split(StringConstants.FRONT_SLASH)) {
            if(!route.equals(StringConstants.EMPTY_STRING)) {
                pathBuilder.append(StringConstants.FRONT_SLASH);
                pathBuilder.append(route);
            }
        }
        return pathBuilder.toString();
    }
}
