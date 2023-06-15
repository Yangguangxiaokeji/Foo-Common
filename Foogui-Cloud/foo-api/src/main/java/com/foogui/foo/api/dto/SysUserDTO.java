package com.foogui.foo.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 用户信息DTO
 *
 * @author Foogui
 * @date 2023/05/27
 */
@Data
public class SysUserDTO implements Serializable {


    private static final long serialVersionUID = 5380659942000359046L;

    /**
     * id
     */
    private Long id;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime createTime;

    private String updateUser;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime updateTime;

    private Integer version;

    private Integer deleted;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 权限
     */
    private Set<String> permissions;
    /**
     * 角色
     */
    private Set<String> roles;

}
