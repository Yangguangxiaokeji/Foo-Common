package com.foogui.foo.common.web.utils;

import com.foogui.foo.common.core.constant.HttpConstant;
import com.foogui.foo.common.core.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * servlet工具类
 * 主要用于获取request和response相关信息
 *
 * @author Foogui
 * @date 2023/05/02
 */
@Slf4j
public class ServletUtils {


    /**
     * 获取request参数
     *
     * @param name 名字
     * @return {@link String}
     */
    public static String getParameter(String name) {
        if (getRequest() != null) {
            return getRequest().getParameter(name);
        }
        return null;
    }


    /**
     * 获取request,返回值有可能为null
     *
     * @return {@link HttpServletRequest}
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        return null;
    }


    /**
     * 获取response,返回值有可能为null
     *
     * @return {@link HttpServletResponse}
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getResponse();
        }
        return null;
    }

    /**
     * 获取session
     *
     * @return {@link HttpSession}
     */
    public static HttpSession getSession() {
        if (getRequest() != null) {
            return getRequest().getSession();
        }
        return null;
    }


    /**
     * 根据请求头名获取其值
     *
     * @param headerName 请求头名
     * @return {@link String}
     */
    public static String getHeaderValue(String headerName) {
        if (getRequest() != null) {
            String headerValue = getRequest().getHeader(headerName);
            if (StringUtils.isBlank(headerName)) {
                return StringUtils.EMPTY;
            }
            return headerValue;
        }
        return StringUtils.EMPTY;
    }

    /**
     * 将JSON渲染到前端
     *
     * @param result JSON字符串
     */
    public static void render2Front(String result) {
        HttpServletResponse response = getResponse();
        if (response != null) {
            response.setStatus(ErrorCode.SUCCESS.getCode());
            response.setContentType(HttpConstant.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstant.UTF_8);
            try (PrintWriter writer = response.getWriter()) {
                writer.print(result);
            } catch (IOException e) {
                log.error("render {} to web occurs error ", result, e);
            }
        }

    }

}
