package com.h12.hqueue.di.annotation;

import com.h12.hqueue.util.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
public @interface AutoWire {
    String qualifier() default Constants.DEFAULT_BEAN_NAME;
}
