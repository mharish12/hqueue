package com.h12.hq;

import com.h12.hq.exception.HQValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public abstract class HQValidator implements IValidator {
    protected final ValidatorFactory validatorFactory;
    private final Validator validator;
    private final ExecutableValidator executableValidator;


    public HQValidator() {
        this(Validation.buildDefaultValidatorFactory());
    }

    public HQValidator(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
        this.validator = validatorFactory.getValidator();
        this.executableValidator = validator.forExecutables();
    }

    @Override
    public void validate(Object o) throws HQValidationException {
        HQValidationException exception = null;
        try {
            Set<ConstraintViolation<Object>> violations = validatorFactory.getValidator().validate(o);
            exception = makeMessage(violations);
        } catch (Exception e) {
            exception = new HQValidationException(e.getCause());
            exception.addSuppressed(e);
        } finally {
            if (exception != null) {
                throw exception;
            }
        }
    }

    @Override
    public void validate(Object classObject, Method method, Object... args) throws HQValidationException {
        HQValidationException exception = null;
        try {
            Set<ConstraintViolation<Object>> violations = executableValidator.validateParameters(classObject, method, args);
            exception = makeMessage(violations);
        } catch (Exception e) {
            exception = new HQValidationException(e.getCause());
            exception.addSuppressed(e);
        } finally {
            if (exception != null) {
                throw exception;
            }
        }
    }

    @Override
    public void validateReturnValue(Object classObject, Method method, Object returnValue) {
        HQValidationException exception = null;
        try {
            Set<ConstraintViolation<Object>> violations = executableValidator.validateReturnValue(classObject, method, returnValue);
            exception = makeMessage(violations);
        } catch (Exception e) {
            exception = new HQValidationException(e.getCause());
            exception.addSuppressed(e);
        } finally {
            if (exception != null) {
                throw exception;
            }
        }
    }

    @Override
    public void validate(Field field, Object classObject) throws HQValidationException {
        HQValidationException exception = null;
        try {
            Set<ConstraintViolation<Object>> violations = validator.validateProperty(classObject, field.getName());
            exception = makeMessage(violations);
        } catch (Exception e) {
            exception = new HQValidationException(e.getCause());
            exception.addSuppressed(e);
        } finally {
            if (exception != null) {
                throw exception;
            }
        }
    }


    private HQValidationException makeMessage(Set<ConstraintViolation<Object>> violations) {
        HQValidationException exception = null;
        if (violations != null && !violations.isEmpty()) {
            exception = new HQValidationException();
            for (ConstraintViolation<Object> constraintViolation : violations) {
                exception.addSuppressed(new HQValidationException(constraintViolation.getMessage()));
            }
        }
        return exception;
    }
}
