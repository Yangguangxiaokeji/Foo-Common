package com.foogui.foo.common.web.foo.modules.log.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foogui.foo.common.mybatis.domain.BaseLogicPO;
import com.foogui.foo.common.core.enums.Action;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@TableName("sys_log")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysLogPO extends BaseLogicPO implements Serializable {

    private static final long serialVersionUID = 7942087370094852915L;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public SysLogVO convert2VO() {
        SysLogVO vo = new SysLogVO();
        BeanUtils.copyProperties(this, vo);
        return vo;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public SysLogDTO convert2DTO() {
        SysLogDTO dto = new SysLogDTO();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
