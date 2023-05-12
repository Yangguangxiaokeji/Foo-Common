package com.foogui.foo.common.core.filter;

import com.alibaba.fastjson2.JSON;
import com.foogui.foo.common.core.constant.HttpConstant;
import com.foogui.foo.common.core.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 防止绕过网关直接访问微服务
 *
 * @author Foogui
 * @date 2023/05/12
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE+1)
public class GatewayFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("GatewayFilter is Initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, javax.servlet.FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String fromWhere = request.getHeader(HttpConstant.FROM_WHERE);
        if (StringUtils.isBlank(fromWhere) ||
                !StringUtils.equalsAnyIgnoreCase(fromWhere, HttpConstant.INNER, HttpConstant.OUTER)) {
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
