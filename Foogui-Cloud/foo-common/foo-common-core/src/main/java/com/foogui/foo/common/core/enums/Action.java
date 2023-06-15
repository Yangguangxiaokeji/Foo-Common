package com.foogui.foo.common.core.enums;

/**
 * 行为类型
 *
 * @author Foogui
 * @date 2023/05/02
 */
public enum Action {
    /**
     * 默认行为
     */
    DEFAULT,

    /**
     * 新增行为
     */
    INSERT,

    /**
     * 修改行为
     */
    UPDATE,

    /**
     * 删除行为
     */
    DELETE,

    /**
     * 鉴权行为，包含注册，登录，登出等
     */
    AUTH,

    /**
     * 导出行为
     */
    EXPORT,

    /**
     * 导入行为
     */
    IMPORT,

}
