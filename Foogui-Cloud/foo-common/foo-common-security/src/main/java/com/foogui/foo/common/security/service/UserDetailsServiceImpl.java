package com.foogui.foo.common.security.service;

import com.foogui.foo.common.security.domain.UserInfo;
import com.foogui.foo.common.security.domain.UserInfoDetail;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


/**
 * 查询用户信息
 *
 * @author Foogui
 * @date 2023/05/24
 */
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 模拟根据username从DB中查询的结果
        // RPC
        UserInfo userInfo=new UserInfo();
        userInfo.setUsername("root");
        userInfo.setPassword("root");
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户名"+username+"不存在，登陆失败。");
        }


        // 模拟查询权限
        // 实际来源于数据库,注意前缀是ROLE_
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");
        // 将查到的用户名，密码，权限进一步传递，接下来会去校验密码
        UserInfoDetail userInfoDetail = new UserInfoDetail(userInfo.getUsername(), userInfo.getPassword(), authorities);
        userInfoDetail.setUserInfo(userInfo);
        return userInfoDetail;

    }
}
