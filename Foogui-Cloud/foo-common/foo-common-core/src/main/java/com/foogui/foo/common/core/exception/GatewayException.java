package com.foogui.foo.common.core.exception;

import com.foogui.foo.common.core.enums.ResponseCode;
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

    public GatewayException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.description = responseCode.getMessage();

    }

    public GatewayException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(), cause);
        this.code = responseCode.getCode();
        this.description = responseCode.getMessage();
    }
}
