package com.foogui.foo.common.web.foo.common.security.exception;

import com.foogui.foo.common.core.enums.ResponseCode;
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

    public SecurityAuthorityException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();

    }

    public SecurityAuthorityException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(), cause);
        this.code = responseCode.getCode();
    }
}
