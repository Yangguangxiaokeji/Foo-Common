package com.foogui.foo.auth.service.impl;

import com.foogui.foo.api.service.FeignSysUserService;
import com.foogui.foo.auth.service.AuthService;
import com.foogui.foo.common.core.constant.CacheConstant;
import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.core.utils.JwtUtil;
import com.foogui.foo.common.redis.service.RedisObjectUtil;
import com.foogui.foo.common.security.domain.LoginUser;
import com.foogui.foo.common.security.domain.LoginUserDetail;
import com.foogui.foo.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private FeignSysUserService feignSysUserService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private RedisObjectUtil redisObjectUtil;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    /**
     * 登录
     * 请求能到这说明已经认证完毕了
     * @param loginUser 登录用户
     * @return {@link Result}<{@link String}>
     */
    @Override
    public Result<String> login(LoginUser loginUser) {
        // 获取前端输入的账号密码
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();

        // Result<SysUserDTO> sysUserDTOResult = feignSysUserService.queryByUsername(username);
        //
        //
        // // todo：密码连续输入3次失败后，锁定10分钟（可以对key-value中增加retryTime属性）
        // if (!passwordEncoder.matches(password, user.getPassword())) {
        //     throw new AuthException("用户名或者密码错误!");
        // }
        //
        // // 模拟查询权限，实际来源于数据库,注意前缀是ROLE_
        // // todo:根据userid查询权限集合，这里其实在modules中已经包装在dto中传过来了
        // List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_user:searchById");
        // // 将查到的用户名，密码，权限进一步传递，接下来会去校验密码
        // LoginUserDetail loginUserDetail = new LoginUserDetail(loginUser.getUsername(), loginUser.getPassword(), authorities);
        // loginUserDetail.setLoginUser(loginUser);
        LoginUserDetail currentUser = SecurityUtils.getCurrentUser();


        // todo : 创建token返回前端
        String token = "test token";

        String userId = currentUser.getLoginUser().getUserId();
        HashMap<String, String> payload = new HashMap<>();
        payload.put(CacheConstant.JWT_PAYLOAD_KEY, userId);
        // 30min有效
        String jwt = jwtUtil.createJwt(payload, Calendar.MINUTE, 30);
        // UserInfoDetail存入redis
        redisObjectUtil.set(CacheConstant.LOGIN_TOKEN + userId, currentUser, 30L, TimeUnit.MINUTES);
        // // todo：rpc记录登录log
        return Result.success(jwt,"登录成功");
    }

    @Override
    public Result<String> logout() {
        LoginUserDetail currentUser = SecurityUtils.getCurrentUser();
        String userId = currentUser.getLoginUser().getUserId();
        redisObjectUtil.del(CacheConstant.LOGIN_TOKEN + userId);
        // todo: 记录登出log
        return Result.success("退出成功");
    }

    private boolean checkPassword(String password) {
        //    判断账户时候被锁定
        String userId ="";
        String lockKey="login:lock"+userId;
        //    核对密码
        String numberKey="login:number"+userId;
        return true;
    }
}
