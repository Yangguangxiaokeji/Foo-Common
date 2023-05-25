package com.foogui.foo.common.security.domain;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author Foogui
 * @date 2023/05/24
 */
@Data
public class UserInfo {

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

    private String createUser;

    private LocalDateTime createTime;


    private String updateUser;

    private LocalDateTime updateTime;


    private Integer version;

    private int deleted ;

}
