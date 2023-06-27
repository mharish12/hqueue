package com.h12.hqueue.di.annotation;

import com.h12.hqueue.util.Constants;
import com.h12.hqueue.util.StringConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Controller {
    String path() default StringConstants.EMPTY_STRING;
    String qualifier() default Constants.DEFAULT_BEAN_NAME;
}
