package com.h12.hq.di.annotation;

import com.h12.hq.util.Config;
import com.h12.hq.util.StringConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Controller {
    String path() default StringConstants.EMPTY_STRING;

    String qualifier() default Config.DEFAULT_BEAN_NAME;
}
