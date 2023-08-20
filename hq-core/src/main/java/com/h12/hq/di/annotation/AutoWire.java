package com.h12.hq.di.annotation;

import com.h12.hq.util.Config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
public @interface AutoWire {
    String qualifier() default Config.DEFAULT_BEAN_NAME;
}
