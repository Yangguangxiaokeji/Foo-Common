package com.foogui.foo.gateway.filter;

import com.foogui.foo.common.core.constant.HttpConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 预处理过滤器
 * 提前处理request，过滤前缀，避免yaml中每个route都配置StripPrefix
 * @author Foogui
 * @date 2023/05/04
 */
@Component
public class PreprocessFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 内部请求来源参数清除
        ServerHttpRequest request = exchange.getRequest().mutate().headers(header -> {
            header.remove(HttpConstant.INNER_REQUEST);
        }).build();
        // 重写StripPrefix
        ServerWebExchangeUtils.addOriginalRequestUrl(exchange, request.getURI());
        String rawPath = request.getURI().getRawPath();
        String newPath = StringUtils.substring(rawPath, StringUtils.ordinalIndexOf(rawPath, "/" , 2));
        ServerHttpRequest newRequest = request.mutate().path(newPath).build();
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

        return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
