package com.h12.hq.exception;

public class HQValidationException extends RuntimeException {
    public HQValidationException() {
        super();
    }

    public HQValidationException(String msg) {
        super(msg);
    }

    public HQValidationException(Throwable cause) {
        super(cause);
    }
}
