package com.h12.hq.server.exception;

import com.h12.hq.exception.ServerException;

public class HttpServerException extends ServerException {
    public HttpServerException(String msg) {
        super(msg);
    }

    public HttpServerException(Throwable cause) {
        super(cause);
    }
}
