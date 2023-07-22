package com.h12.hq.di.annotation;

import com.h12.hq.util.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Bean {
    String qualifier() default Constants.DEFAULT_BEAN_NAME;
}
