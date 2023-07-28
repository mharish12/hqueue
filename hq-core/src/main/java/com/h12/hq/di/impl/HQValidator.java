package com.h12.hq.di.impl;

import com.h12.hq.IValidator;
import com.h12.hq.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class HQValidator implements IValidator {
//    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public HQValidator() {
        this(Validation.buildDefaultValidatorFactory());
    }
    public HQValidator(ValidatorFactory validatorFactory) {
//        this.validatorFactory = validatorFactory;
        this.validator = validatorFactory.getValidator();
    }

    @Override
    public void validate(Object o) throws ValidationException {
        try {
            Set<ConstraintViolation<Object>> violations =  validator.validate(o);

            if(violations.size() > 0) {
                for (ConstraintViolation<Object> objectConstraintViolation: violations){
                }
            }
        } catch (Exception e) {
            ValidationException validationException = new ValidationException(e.getMessage());
            validationException.addSuppressed(e);
            throw e;
        }

    }
}
