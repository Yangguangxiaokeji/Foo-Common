package com.foogui.foo.common.security.exception;

import com.foogui.foo.common.core.enums.ResponseCode;
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

    public SecurityAuthException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();

    }

    public SecurityAuthException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(), cause);
        this.code = responseCode.getCode();
    }
}
