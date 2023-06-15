package com.foogui.foo.common.web.foo.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.web.foo.gateway.utils.WebFluxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关全局异常处理器
 *
 * @author Foogui
 * @date 2023/05/04
 */
@Component
@Order(-1) // 保证优先级高于默认的 Spring Cloud Gateway 的 ErrorWebExceptionHandler 实现
@Slf4j
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        // 已经 commit，则直接返回异常
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        Result<?> result;
        if (ex instanceof ResponseStatusException) {
            result = responseStatusExceptionHandler(exchange, (ResponseStatusException) ex);
        } else {
            result = defaultExceptionHandler(exchange, ex);
        }
        //  TODO 选择性保存日志

        return WebFluxUtils.webFluxWrite(response, exchange, result);
    }

    /**
     * 处理网关其他异常，兜底处理所有的一切
     *
     * @param exchange 上下文对象
     * @param ex       异常
     * @return {@link Result}<{@link ?}>
     */
    private Result<?> defaultExceptionHandler(ServerWebExchange exchange, Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[defaultExceptionHandler][uri({}:{}) 发生异常:{}]", request.getURI(), request.getMethod(), ex.getMessage());
        return Result.fail(ex, ex.getMessage());
    }


    /**
     * 处理 Spring Cloud Gateway 默认抛出的 ResponseStatusException 异常
     *
     * @param exchange 上下文对象
     * @param ex       异常
     * @return {@link Result}<{@link ?}>
     */
    private Result<?> responseStatusExceptionHandler(ServerWebExchange exchange, ResponseStatusException ex) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[responseStatusExceptionHandler][uri({}:{}) 发生异常:{}]", request.getURI(), request.getMethod(), ex.getMessage());
        if (ex instanceof NotFoundException) {
            return Result.fail(ex, "服务未找到");
        }else if (ex instanceof ServerErrorException){
            return Result.fail(ex, "服务器发生错误");
        }
        // Todo...这里可以对ResponseStatusException异常做更精细的处理
        return Result.fail(ex, ex.getReason());

    }
}
