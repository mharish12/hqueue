package com.h12.hq.di.annotation;

import com.h12.hq.util.Constants;
import com.h12.hq.util.StringConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Component {
    String qualifier() default Constants.DEFAULT_BEAN_NAME;
}
