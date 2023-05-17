package com.foogui.foo.common.core.exception;

import com.foogui.foo.common.core.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 5294969255532501296L;

    /**
     * 错误编码
     */
    private int code;

    /**
     * 描述信息
     */
    private String description;

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.description = responseCode.getMessage();

    }

    public BizException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(), cause);
        this.code = responseCode.getCode();
        this.description = responseCode.getMessage();
    }
}
