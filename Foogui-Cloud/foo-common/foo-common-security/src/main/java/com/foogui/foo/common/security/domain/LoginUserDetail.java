package com.foogui.foo.common.web.foo.common.security.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录用户详细信息
 *
 * @author Foogui
 * @date 2023/05/27
 */
@Getter
@Setter
public class LoginUserDetail implements UserDetails, Serializable {

    private static final long serialVersionUID = 5852366674192238325L;
    /**
     * 用户信息
     */
    private LoginUser loginUser;


    /**
     * 权限集合
     */
    private Set<String> permissions;

    /**
     * 角色集合
     */
    private Set<String> roles;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户名
     */
    private  String username;


    @JsonIgnore     //不参与序列化,因为 SimpleGrantedAuthority 没有无参构造，反序列化会出错
    private  Set<GrantedAuthority> authorities;

    private  boolean accountNonExpired;

    private  boolean accountNonLocked;

    private  boolean credentialsNonExpired;

    private  boolean enabled;

    public LoginUserDetail() {
    }

    public LoginUserDetail(String username, String password, Set<GrantedAuthority> authorities) {
        this.password = password;
        this.username = username;
        this.authorities = authorities;
        this.permissions=new HashSet<>();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
        //把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
