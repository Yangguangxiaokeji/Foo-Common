package com.foogui.foo.common.core.exception;

import com.foogui.foo.common.core.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthException extends RuntimeException{


    private static final long serialVersionUID = 484667328921268033L;
    /**
     * 错误编码
     */
    private int code;

    /**
     * 描述信息
     */
    private String description;
    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.description = responseCode.getMessage();

    }

    public AuthException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(), cause);
        this.code = responseCode.getCode();
        this.description = responseCode.getMessage();
    }
}
