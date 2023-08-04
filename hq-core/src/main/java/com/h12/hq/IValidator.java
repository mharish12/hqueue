package com.h12.hq;

import com.h12.hq.exception.ValidationException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface IValidator {
    void validate(Object o) throws ValidationException;
    void validate(Method method) throws ValidationException;
    void validate(Parameter parameter) throws ValidationException;
    void validate(Field field) throws ValidationException;
}
