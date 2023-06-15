package com.foogui.foo.common.web.foo.modules.log.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foogui.foo.common.core.enums.Action;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysLogDTO implements Serializable {

    private static final long serialVersionUID = -2842034525170321992L;

    private Long id;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime createTime;

    private String updateUser;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime updateTime;

    private Integer version;

    private Integer deleted;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public SysLogPO convert2PO() {
        SysLogPO po = new SysLogPO();
        BeanUtils.copyProperties(this, po);
        return po;
    }


}
