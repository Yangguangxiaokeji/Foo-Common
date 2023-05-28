package com.foogui.foo.common.security.handler;

import com.foogui.foo.common.core.exception.AuthException;
import com.foogui.foo.common.core.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未授权的统一处理方式
 *
 * @author Foogui
 * @date 2023/05/27
 */
//todo 暂时不好用
public class UnAuthorizedEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(UnAuthorizedEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof AuthenticationServiceException) {
            // 获取原始异常信息
            Throwable cause = authException.getCause();
            // 判断是否为自定义异常
            if (cause instanceof AuthException) {
                ResponseUtils.write2frontFail(response, cause.getMessage());
            }
        }
        logger.info("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ResponseUtils.write2frontFail(response, "未授权");
    }
}
