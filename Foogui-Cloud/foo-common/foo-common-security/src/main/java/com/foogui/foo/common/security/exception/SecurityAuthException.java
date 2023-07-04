package com.foogui.foo.common.security.exception;

import com.foogui.foo.common.core.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;


@Getter
@Setter
public class SecurityAuthException extends AuthenticationException {


    private static final long serialVersionUID = 484667328921268033L;
    /**
     * 错误编码
     */
    private int code;


    public SecurityAuthException(String message) {
        super(message);
    }

    public SecurityAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityAuthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();

    }

    public SecurityAuthException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode.getCode();
    }
}
