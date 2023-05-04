package com.foogui.foo.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final String[] skipAuthUrls = {"/login" , "/logout" , "/register" , "/redirect"};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求url地址
        String url =
                exchange.getRequest().getURI().getPath();
        // 跳过不需要验证的路径
        if (null != skipAuthUrls &&
                isSkipUrl(url)) {
            return chain.filter(exchange);
        }

        // 从请求头中取得token
        String token =
                exchange.getRequest().getHeaders().getFirst(
                        "Authorization");
        // 判断前端是否出携带了token
        if (StringUtils.isEmpty(token)) {
            System.out.println("鉴权失败");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 解析token是否合法

        // 调用chain.filter继续向下游执行
        return chain.filter(exchange);
    }

    /**
     * 判断当前访问的url是否开头URI是在配置的忽略
     * url列表中
     *
     * @param url
     * @return
     */
    public boolean isSkipUrl(String url) {
        for (String skipAuthUrl : skipAuthUrls) {
            if (url.startsWith(skipAuthUrl)) {
                return true;
            }
        }
        return false;
    }

    // 顺序,数值越小,优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
