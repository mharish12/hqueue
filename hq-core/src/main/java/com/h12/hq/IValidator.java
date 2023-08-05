package com.h12.hq;

import com.h12.hq.exception.HQValidationException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface IValidator {
    void validate(Object o) throws HQValidationException;
    void validate(Object classObject, Method method, Object ...args) throws HQValidationException;
    void validateReturnValue(Object classObject, Method method, Object returnValue) throws HQValidationException;
//    void validate(Parameter parameter, Object classObject, Object ...args) throws HQValidationException;
    void validate(Field field, Object classObject) throws HQValidationException;
}
