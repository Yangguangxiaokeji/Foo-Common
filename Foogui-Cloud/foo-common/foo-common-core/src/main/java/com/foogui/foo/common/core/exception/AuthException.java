package com.foogui.foo.common.core.exception;

import com.foogui.foo.common.core.enums.ErrorCode;
import lombok.Data;

@Data
public class AuthException extends RuntimeException{

    /**
     * 错误编码
     */
    private int code;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();

    }

    public AuthException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode.getCode();
    }
}
