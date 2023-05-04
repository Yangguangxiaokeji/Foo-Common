package com.foogui.foo.gateway.filter;

import com.foogui.foo.common.core.constant.HttpConstant;
import com.foogui.foo.common.core.exception.AuthorizationException;
import com.foogui.foo.gateway.utils.WebFluxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private final String[] skipAuthUrls = {"/login", "/logout", "/register", "/redirect"};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 获取请求url地址
        String url = request.getURI().getPath();
        // 跳过不需要验证的路径
        if (null != skipAuthUrls && isSkipUrl(url)) {
            return chain.filter(exchange);
        }

        // 从请求头中取得token
        String token = request.getHeaders().getFirst(HttpConstant.Authorization);
        // 判断前端是否出携带了token
        if (StringUtils.isBlank(token)) {
            log.info("token不能为空");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return WebFluxUtils.webFluxWrite(response, exchange,new AuthorizationException("token不能为空"));
        }
        // 解析token是否合法


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

    @Override
    public int getOrder() {
        return 0;
    }
}
