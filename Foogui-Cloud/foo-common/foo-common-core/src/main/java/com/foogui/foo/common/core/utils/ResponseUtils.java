package com.foogui.foo.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.foogui.foo.common.core.domain.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {
    public static void write2frontSuccess(HttpServletResponse response, String data)  {
        // 允许跨域
        setResponseHeaders(response);
        String result = JSON.toJSONString(Result.success(data));
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void write2frontFail(HttpServletResponse response, String message)  {
        setResponseHeaders(response);
        String result = JSON.toJSONString(Result.fail(message));
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置响应头
     *
     * @param response 响应
     */
    private static void setResponseHeaders(HttpServletResponse response) {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "Authorization, authorization,token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
