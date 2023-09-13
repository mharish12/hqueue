package com.h12.hq.exception;

public class HQServerException extends RuntimeException {
    public HQServerException(String msg) {
        super(msg);
    }

    public HQServerException(Throwable cause) {
        super(cause);
    }
}
