package com.foogui.foo.common.core.exception;

public class GatewayException extends RuntimeException  {
    public GatewayException() {
        super();
    }

    public GatewayException(String message) {
        super(message);
    }

    public GatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
