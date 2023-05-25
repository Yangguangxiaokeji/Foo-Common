package com.foogui.foo.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foogui.foo.common.core.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt身份验证过滤器
 * 定义在UsernamePasswordAuthenticationFilter前
 * 获取信息存入SecurityContextHolder
 * @author Foogui
 * @date 2023/05/24
 */
@Slf4j
public class JwtAuthenticationFilter implements Filter {


    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 如果是login请求直接放行
        //TODO:实现请求路径的判断
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = httpRequest.getHeader(SecurityConstant.AUTHENTICATION);
        log.info("Authentication token is"+token);
        // if (StringUtils.isBlank(token)) {
        //     httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //     return;
        // }
        // 获取请求体的输入流
        // InputStream inputStream = httpRequest.getInputStream();

        // 将输入流中的JSON数据转换为Java对象
        // UserInfo userInfo = objectMapper.readValue(inputStream, UserInfo.class);

        // 在这里对Java对象进行处理
        chain.doFilter(request, response);
    }
}
