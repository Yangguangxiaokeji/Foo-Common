package com.foogui.foo.common.core.exception;

import com.foogui.foo.common.core.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatewayException extends RuntimeException  {

    private static final long serialVersionUID = 7922028982540468818L;
    /**
     * 错误编码
     */
    private int code;

    /**
     * 描述信息
     */
    private String description;
    public GatewayException() {
        super();
    }

    public GatewayException(String description) {
        super(description);
    }

    public GatewayException(String description, Throwable cause) {
        super(description, cause);
    }

    public GatewayException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getMessage();

    }

    public GatewayException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode.getCode();
        this.description = errorCode.getMessage();
    }
}
