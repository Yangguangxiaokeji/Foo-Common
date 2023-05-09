package com.foogui.foo.gateway.filter;

import com.foogui.foo.common.core.constant.FilterOrderConstant;
import com.foogui.foo.common.core.constant.HttpConstant;
import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.dao.redis.RedisUtil;
import com.foogui.foo.gateway.utils.WebFluxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CheckCodeFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisUtil redisUtil;

    private final String[] skipCheckUrls = {"/auth/login", "/auth/register","/demo/test"};
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (!StringUtils.containsAny(request.getURI().getPath(), skipCheckUrls)){
            return chain.filter(exchange);
        }
        // 校验验证码
        String codeKey = request.getHeaders().getFirst(HttpConstant.CODE_KEY);
        // String code = exchange.getRequiredAttribute("code");
        String code = request.getQueryParams().getFirst("code");
        if (code.equals(redisUtil.get(codeKey))){
            redisUtil.delete(codeKey);
            return chain.filter(exchange);
        }
        return WebFluxUtils.webFluxWrite(exchange.getResponse(), exchange, Result.fail("验证码不正确"));
    }

    @Override
    public int getOrder() {
        return FilterOrderConstant.CHECK_CODE_FILTER;
    }
}
