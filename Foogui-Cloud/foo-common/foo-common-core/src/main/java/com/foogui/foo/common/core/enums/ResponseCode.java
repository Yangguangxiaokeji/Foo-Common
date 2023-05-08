package com.foogui.foo.common.core.enums;

import lombok.Getter;

/**
 * http响应码
 *
 * @author Foogui
 * @date 2023/05/02
 */
@Getter
public enum ResponseCode {

    // 错误码
    SUCCESS(200, "操作成功"),
    REDIRECT(302, "重定向"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN_ACCESS(403, "权限不足禁止访问"),
    NOT_FOUND(404, "资源未找到"),
    WRONG_REQUEST_METHOD(405, "请求方式错误"),
    PARAM_ERROR(400, "请求参数错误"),
    ERROR(500, "服务器内部出现错误"), UN_KNOWN(50001, "未知错误");
    /**
     * 编码
     */
    private final int code;

    /**
     * 描述信息
     */
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
