package com.h12.hq;

import com.h12.hq.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public abstract class HQValidator implements IValidator {
    protected final ValidatorFactory validatorFactory;

    public HQValidator() {
        this(Validation.buildDefaultValidatorFactory());
    }

    public HQValidator(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    @Override
    public void validate(Object o) throws ValidationException {
        try {
            Set<ConstraintViolation<Object>> violations = validatorFactory.getValidator().validate(o);

            if (!violations.isEmpty()) {
                for (ConstraintViolation<Object> constraintViolation : violations) {

                }
            }
        } catch (Exception e) {
            ValidationException validationException = new ValidationException(e.getMessage());
            validationException.addSuppressed(e);
            throw e;
        }
    }
}
