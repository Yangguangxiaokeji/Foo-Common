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
 *
 * 后续在GlobalExceptionHandler中用于异常捕获，记录导致异常的请求参数到错误日志
 * ExceptionHandler中可以传入ServletRequest作为入参，但ServletRequest的inputStream只能被读取一次
 * 发生异常的时候再想去读取body只能得到一个已经Closed的Stream
 *
 * @author Foogui
 * @date 2023/05/12
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RequestAgainFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        ServletRequest decorator = new HttpServletRequestAgainWrapper(request);
        filterChain.doFilter(decorator, servletResponse);
    }
}
