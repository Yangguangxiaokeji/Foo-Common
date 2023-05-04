package com.foogui.foo.utils.http;

import com.foogui.foo.common.core.constant.HttpConstant;
import com.foogui.foo.common.core.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * servlet工具类
 * 主要用于获取request和response相关信息
 *
 * @author Foogui
 * @date 2023/05/02
 */
@Slf4j
public class ServletHelper {


    /**
     * 获取request参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }


    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getRequest();

    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }


    /**
     * 根据key从请求头获取其值
     */
    public static String getHeader(String headerName) {
        String headerValue = getRequest().getHeader(headerName);
        if (StringUtils.isBlank(headerName)) {
            return StringUtils.EMPTY;
        }
        return headerValue;
    }

    /**
     * 渲染到前端
     *
     * @param result  字符串
     */
    public static void render2Front(String result) {
        try {
            HttpServletResponse response = getResponse();
            response.setStatus(ResponseCode.SUCCESS.getCode());
            response.setContentType(HttpConstant.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstant.UTF_8);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
