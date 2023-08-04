package com.h12.hq.server.http;

import com.h12.hq.DependencyManager;
import com.h12.hq.HQValidator;
import com.h12.hq.exception.ValidationException;
import jakarta.validation.executable.ExecutableValidator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public abstract class AbstractHandler extends HQValidator implements IResolver {

    public AbstractHandler() {
        super();
    }

    @Override
    public void resolve(Method method, Object methodClassObject, DependencyManager dependencyManager, Object ...args) {
        //TODO: implement to get all the details required to resolve this method.

    }

    @Override
    public void validate(Method method) throws ValidationException {
        ExecutableValidator executableValidator = validatorFactory.getValidator().forExecutables();

    }

    @Override
    public void validate(Parameter parameter) throws ValidationException {

    }

    @Override
    public void validate(Field field) throws ValidationException {

    }
}
