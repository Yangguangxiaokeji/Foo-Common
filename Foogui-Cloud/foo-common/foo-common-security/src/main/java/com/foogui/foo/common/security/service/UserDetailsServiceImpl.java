package com.foogui.foo.common.web.foo.common.security.service;

import com.foogui.foo.common.web.foo.api.dto.SysUserDTO;
import com.foogui.foo.common.web.foo.api.service.FeignSysUserService;
import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.web.foo.common.security.exception.SecurityAuthException;
import com.foogui.foo.common.core.utils.SpringBeanUtils;
import com.foogui.foo.common.web.foo.common.security.domain.LoginUser;
import com.foogui.foo.common.web.foo.common.security.domain.LoginUserDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 查询用户信息
 *
 * @author Foogui
 * @date 2023/05/24
 */
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {


    private FeignSysUserService feignSysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        feignSysUserService= SpringBeanUtils.getBean(FeignSysUserService.class);
        Result<SysUserDTO> sysUserDTOResult = feignSysUserService.queryByUsername(username);
        // todo：考虑rpc返回fail？
        SysUserDTO user = sysUserDTOResult.getData();
        if (user == null) {
            throw new SecurityAuthException("用户名不存在或远程服务调用失败");
        } else if ("1".equals(user.getStatus())) {
            throw new SecurityAuthException("用户停用了");
        } else if (user.getPermissions() == null) {
            throw new SecurityAuthException("该用户无任何权限");
        }
        LoginUser loginUser =new LoginUser();
        BeanUtils.copyProperties(user, loginUser);
        //todo:遍历SysUserDTO的roles处理，包装权限

        String collect = user.getPermissions().stream().collect(Collectors.joining(","));
        Set<GrantedAuthority> authorities =new HashSet<>(AuthorityUtils.commaSeparatedStringToAuthorityList(collect)) ;
        // 将查到的用户名，密码，权限进一步传递，接下来会去校验密码
        LoginUserDetail loginUserDetail = new LoginUserDetail(loginUser.getUsername(), loginUser.getPassword(), authorities);
        loginUserDetail.setLoginUser(loginUser);
        loginUserDetail.setPermissions(user.getPermissions());
        loginUserDetail.setRoles(user.getRoles());

        return loginUserDetail;

    }
}
