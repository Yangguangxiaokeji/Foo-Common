package com.foogui.foo.common.web.foo.common.security.handler;

import com.foogui.foo.common.core.constant.CacheConstant;
import com.foogui.foo.common.core.constant.SecurityConstant;
import com.foogui.foo.common.core.utils.JwtUtil;
import com.foogui.foo.common.core.utils.ResponseUtils;
import com.foogui.foo.common.web.foo.common.redis.service.RedisObjectUtil;
import com.foogui.foo.common.web.foo.common.security.domain.LoginUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登出业务处理
 *
 * @author Foogui
 * @date 2023/05/27
 */
public class TokenLogoutHandler implements LogoutHandler {
    private RedisObjectUtil redisObjectUtil;

    private JwtUtil jwtUtil;

    public TokenLogoutHandler(RedisObjectUtil redisObjectUtil, JwtUtil jwtUtil) {
        this.redisObjectUtil = redisObjectUtil;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(SecurityConstant.AUTHENTICATION);
        if (token != null) {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            LoginUserDetail loginUserDetail = (LoginUserDetail) authentication.getPrincipal();
            String userId = loginUserDetail.getLoginUser().getUserId();
            redisObjectUtil.delete(CacheConstant.LOGIN_TOKEN + userId);
            // todo: 记录登出log

        }
        // controller不用写logout方法？
        ResponseUtils.write2frontSuccess(response, "登出成功");
    }
}
