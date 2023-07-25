package com.h12.hq.server.exception;

public class ServerException extends RuntimeException {
    public ServerException(String msg) {
        super(msg);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }
}
