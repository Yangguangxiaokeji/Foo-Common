package com.foogui.foo.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foogui.foo.common.core.utils.JwtUtil;
import com.foogui.foo.common.core.utils.ResponseUtils;
import com.foogui.foo.common.core.utils.StringUtils;
import com.foogui.foo.common.redis.service.RedisObjectUtil;
import com.foogui.foo.common.security.domain.LoginUser;
import com.foogui.foo.common.security.handler.UnAuthorizedEntryPoint;
import com.foogui.foo.common.security.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

/**
 * 登录入口过滤器
 * 只拦截路径为/auth/login的请求，需要对请求进行校验，还未到 Controller不能用jsr303
 * 通过这个过滤器表明账号密码正确，用户权限已经包装在Authentication中
 *
 * @author Foogui
 * @date 2023/05/27
 */

public class LoginEnterFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(UnAuthorizedEntryPoint.class);

    private AuthenticationProvider authenticationProvider;

    private RedisObjectUtil redisObjectUtil;

    private JwtUtil jwtUtil;

    public LoginEnterFilter(AuthenticationManager authenticationManager, AuthenticationProvider authenticationProvider, RedisObjectUtil redisObjectUtil, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.authenticationProvider = authenticationProvider;
        this.redisObjectUtil = redisObjectUtil;
        this.jwtUtil = jwtUtil;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从请求对象中获取输入流
        InputStream inputStream = null;
        LoginUser loginUser = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            inputStream = request.getInputStream();
            // 将输入流转换为 JSON 数据
            loginUser = objectMapper.readValue(inputStream, LoginUser.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (StringUtils.isAnyBlank(loginUser.getUsername(), loginUser.getPassword())) {
            // todo 校验账号密码的范围，细化校验规则
            ResponseUtils.write2frontFail(response, "请输入账号与密码");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), Collections.emptyList());
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);
        SecurityUtils.setAuthentication(authentication);
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        // LoginUserDetail loginUserDetail = (LoginUserDetail) auth.getPrincipal();
        //
        // // todo : 创建token返回前端
        // String token = "test token";
        //
        // String userId = loginUserDetail.getLoginUser().getUserId();
        // HashMap<String, String> payload = new HashMap<>();
        // payload.put(CacheConstant.JWT_PAYLOAD_KEY, userId);
        // // 30min有效
        // String jwt = jwtUtil.createJwt(payload, Calendar.MINUTE, 30);
        // // UserInfoDetail存入redis
        // redisObjectUtil.set(CacheConstant.LOGIN_TOKEN + userId, loginUserDetail, 30L, TimeUnit.MINUTES);

        // ResponseUtils.write2frontFail(response, jwt);
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request,response);

    }

    /**
     * 不成功身份验证，只用于登录请求的处理
     *
     * @param request  请求
     * @param response 响应
     * @param failed   失败
     * @throws IOException      ioexception
     * @throws ServletException servlet异常
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtils.write2frontFail(response, failed.getMessage());
    }


}
