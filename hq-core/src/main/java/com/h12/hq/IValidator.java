package com.h12.hq;

import com.h12.hq.exception.ValidationException;

public interface IValidator {
    void validate(Object o) throws ValidationException;
}
