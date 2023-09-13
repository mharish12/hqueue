package com.h12.hq.server.exception;

import com.h12.hq.exception.HQServerException;

public class HttpHQServerException extends HQServerException {
    public HttpHQServerException(String msg) {
        super(msg);
    }

    public HttpHQServerException(Throwable cause) {
        super(cause);
    }
}
