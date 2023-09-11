package com.h12.hq.di.annotation;

import com.h12.hq.util.StringConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Value {
    String value();

    String defaultValue() default StringConstants.EMPTY_STRING;
}
