package com.foogui.foo.common.security.handler;

import com.foogui.foo.common.core.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理认证失败抛出的异常
 *
 * @author Foogui
 * @date 2023/05/27
 */
@Order(-1)
public class UnAuthorizedEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(UnAuthorizedEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        logger.info("请求访问：{}，认证失败", request.getRequestURI());
        ResponseUtils.write2frontFail(response, authException.getMessage());
    }
}
