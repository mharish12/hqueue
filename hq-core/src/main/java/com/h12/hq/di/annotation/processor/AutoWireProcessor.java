package com.h12.hq.di.annotation.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("com.h12.hq.di.annotation.AutoWire")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class AutoWireProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        throw new RuntimeException("Annotation process stopped");
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : annotatedElements) {
//                annotation.accept()
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Printing message: " + element.getSimpleName().toString());
            }
        }

        // Process the scanned classes or perform any other desired actions
        return false;
    }
}
