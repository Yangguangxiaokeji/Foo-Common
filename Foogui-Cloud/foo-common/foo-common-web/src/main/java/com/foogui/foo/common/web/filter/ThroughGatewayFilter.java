package com.foogui.foo.common.web.filter;

import com.alibaba.fastjson2.JSON;
import com.foogui.foo.common.web.foo.common.core.constant.HttpConstant;
import com.foogui.foo.common.web.foo.common.core.domain.Result;
import com.foogui.foo.common.web.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防止绕过网关直接访问微服务
 *
 * @author Foogui
 * @date 2023/05/12
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class ThroughGatewayFilter implements Filter {

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
            ServletUtils.render2Front(JSON.toJSONString(Result.fail("不能直接绕过网关访问服务")));
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
