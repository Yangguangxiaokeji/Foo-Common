package com.foogui.foo.common.log.domain;

import com.foogui.foo.common.dao.domain.BaseEntity;
import com.foogui.foo.common.log.enums.Action;
import lombok.Data;

import java.io.Serializable;

@Data
public class LogPO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -722166392700755402L;

    /**
     * 日志描述
     */
    private String description;

    /**
     * ip地址
     */

    private String ip;

    /**
     * 用户浏览器
     */
    private String userAgent;

    /**
     * 请求URI
     */
    private String uri;

    /**
     * 行为方式
     */
    private Action action;

    /**
     * 请求的数据
     */
    private String requestData;

    /**
     * 响应的数据
     */
    private String responseData;

    /**
     * 执行时间
     */
    private Long duration;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 内部服务ID
     */
    private String serviceId;
    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

}
