package com.foogui.foo.gateway.enums;

import lombok.Getter;

@Getter
public enum GatewayErrorCode {
    NACOS_ERROR(8848,"NACOS_ERROR");
    /**
     * 编码
     */
    private final int code;

    /**
     * 描述信息
     */
    private final String desc;

    GatewayErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
