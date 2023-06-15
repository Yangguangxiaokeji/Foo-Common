package com.foogui.foo.common.web.foo.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foogui.foo.common.core.constant.CacheConstant;
import com.foogui.foo.common.core.constant.SecurityConstant;
import com.foogui.foo.common.core.utils.JwtUtil;
import com.foogui.foo.common.core.utils.ResponseUtils;
import com.foogui.foo.common.core.utils.StringUtils;
import com.foogui.foo.common.web.foo.common.redis.service.RedisObjectUtil;
import com.foogui.foo.common.web.foo.common.security.domain.LoginUserDetail;
import com.foogui.foo.common.web.foo.common.security.exception.SecurityAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * jwt身份验证过滤器
 * 定义在UsernamePasswordAuthenticationFilter前
 * 获取信息存入SecurityContextHolder
 *
 * @author Foogui
 * @date 2023/05/24
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final List<String> whiteList = new ArrayList<String>(Arrays.asList("/auth/login", "/sys-user/queryByUsername"));

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisObjectUtil redisObjectUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        //todo 考虑更优雅的方式放过校验uri
        if (whiteList.contains(uri)) {
            filterChain.doFilter(request, response);
        } else {
            String token = request.getHeader(SecurityConstant.AUTHENTICATION);
            log.info("Authentication token is" + token);
            if (StringUtils.isBlank(token)) {
                ResponseUtils.write2frontFail(response, "token不能为空");
                return;
            }
            // 解析token
            String userId = null;
            try {
                userId = jwtUtil.verifyJwt(token, CacheConstant.JWT_PAYLOAD_KEY);
            } catch (Exception e) {
                throw new SecurityAuthException(e.getMessage());
            }

            // 从redis中获取用户信息
            Object o = redisObjectUtil.getString(CacheConstant.LOGIN_TOKEN + userId);
            if (o == null) {
                //todo： 跳转到login页面
                ResponseUtils.write2frontFail(response, "登录已过期,请重新登录");
                return;
            }

            LoginUserDetail loginUserDetail = (LoginUserDetail) o;
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDetail, loginUserDetail.getPassword(), loginUserDetail.getAuthorities());
            // 存入SecurityContextHolder，以便FilterSecurityInterceptor鉴权时会取认证对象
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 放行
            filterChain.doFilter(request, response);
        }

    }
}
