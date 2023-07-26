package com.h12.hq.exception;

public class HQException extends RuntimeException {
    public HQException(String msg) {
        super(msg);
    }

    public HQException(Throwable cause) {
        super(cause);
    }

    public HQException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
