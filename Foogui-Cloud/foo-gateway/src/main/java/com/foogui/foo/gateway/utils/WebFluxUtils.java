package com.foogui.foo.gateway.utils;

import com.alibaba.fastjson.JSON;
import com.foogui.foo.common.core.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
public class WebFluxUtils {
    public static Mono<Void> webFluxWrite(ServerHttpResponse response, ServerWebExchange exchange, Throwable ex){
        // 设置 body
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                if (ex==null){
                    return bufferFactory.wrap(JSON.toJSONString(Result.fail()).getBytes(StandardCharsets.UTF_8));
                }
                return bufferFactory.wrap(JSON.toJSONString(Result.fail(ex,ex.getMessage())).getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                ServerHttpRequest request = exchange.getRequest();
                log.error("[writeJSON][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), e);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }

    public static Mono<Void> webFluxWrite(ServerHttpResponse response, ServerWebExchange exchange){
        return webFluxWrite(response,exchange,null);
    }
}
