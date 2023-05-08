package com.foogui.foo.gateway.utils;

import com.alibaba.fastjson.JSON;
import com.foogui.foo.common.core.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
public class WebFluxUtils {
    /**
     * webFlux模型写
     *
     * @param response 响应
     * @param exchange 上下文
     * @param result       异常
     * @return {@link Mono}<{@link Void}>
     */
    public static Mono<Void> webFluxWrite(ServerHttpResponse response, ServerWebExchange exchange, Result<?> result){
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 设置 body
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                return bufferFactory.wrap(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                ServerHttpRequest request = exchange.getRequest();
                log.error("[webFluxWrite][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), e);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }

    public static Mono<Void> webFluxWrite(ServerHttpResponse response, ServerWebExchange exchange){
        return webFluxWrite(response,exchange,null);
    }
}
