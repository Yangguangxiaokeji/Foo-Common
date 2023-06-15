package com.foogui.foo.common.web.foo.modules.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foogui.foo.common.mybatis.domain.BaseLogicPO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@TableName("sys_user")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserPO extends BaseLogicPO implements Serializable {


    private static final long serialVersionUID = 5438124039275358415L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户性别（1男，0女，2未知）
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户类型（0管理员，1普通用户）
     */
    private String userType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public SysUserVO convert2VO() {
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(this, vo);
        return vo;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public SysUserDTO convert2DTO() {
        SysUserDTO dto = new SysUserDTO();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
