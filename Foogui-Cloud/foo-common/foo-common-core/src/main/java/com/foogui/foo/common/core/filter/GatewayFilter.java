package com.foogui.foo.common.core.filter;

import com.alibaba.fastjson2.JSON;
import com.foogui.foo.common.core.constant.HttpConstant;
import com.foogui.foo.common.core.domain.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class GatewayFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("init gateway filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, javax.servlet.FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String gateway = request.getHeader(HttpConstant.FROM_WHERE);
        if(gateway == null || !gateway.equals(HttpConstant.FROM_WHERE_VALUE)){
            System.out.println("======无权访问=======");
            returnJson(servletResponse, JSON.toJSONString(Result.fail("不能直接绕过网关访问服务")));
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("destroy gateway filter");
    }

    private void returnJson(ServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
