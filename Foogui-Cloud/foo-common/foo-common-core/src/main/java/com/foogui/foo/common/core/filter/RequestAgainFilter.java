package com.foogui.foo.common.core.filter;

import com.foogui.foo.common.core.wrapper.HttpServletRequestAgainWrapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求二次读取过滤器
 * 解决 httpServletRequest的流只能读取一次的问题
 * @author Foogui
 * @date 2023/05/12
 */
@Order(Ordered.HIGHEST_PRECEDENCE+1)
public class RequestAgainFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ServletRequest decorator = new HttpServletRequestAgainWrapper(request);
        filterChain.doFilter(decorator, servletResponse);
    }
}
