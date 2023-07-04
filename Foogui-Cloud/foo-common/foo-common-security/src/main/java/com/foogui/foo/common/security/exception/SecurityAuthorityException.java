package com.foogui.foo.common.security.exception;

import com.foogui.foo.common.core.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;


@Getter
@Setter
public class SecurityAuthorityException extends AccessDeniedException {


    private static final long serialVersionUID = 484667328921268033L;
    /**
     * 错误编码
     */
    private int code;


    public SecurityAuthorityException(String message) {
        super(message);
    }

    public SecurityAuthorityException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityAuthorityException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();

    }

    public SecurityAuthorityException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode.getCode();
    }
}
