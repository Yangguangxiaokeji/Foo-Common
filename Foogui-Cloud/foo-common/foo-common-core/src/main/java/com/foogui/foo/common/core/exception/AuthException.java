package com.foogui.foo.common.web.foo.common.core.exception;

import com.foogui.foo.common.web.foo.common.core.enums.ResponseCode;
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

    public AuthException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();

    }

    public AuthException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(), cause);
        this.code = responseCode.getCode();
    }
}
