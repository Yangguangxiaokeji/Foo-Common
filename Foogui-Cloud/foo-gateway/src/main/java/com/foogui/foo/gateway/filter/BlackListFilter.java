package com.foogui.foo.common.web.foo.gateway.filter;

import com.foogui.foo.common.core.constant.FilterOrderConstant;
import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.core.exception.GatewayException;
import com.foogui.foo.common.web.foo.common.redis.service.RedisObjectUtil;
import com.foogui.foo.common.web.foo.gateway.utils.WebFluxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Set;

/**
 * 黑名单局部过滤器
 *
 * @author Foogui
 * @date 2023/05/10
 */
@Component
@ConditionalOnProperty(name="enable.blackList", havingValue="true", matchIfMissing = false)
@Slf4j
public class BlackListFilter implements GlobalFilter, Ordered {
    @Resource
    private RedisObjectUtil redisObjectUtil;

    public static final String BLACK_LIST_KEY = "redis:black:list";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        if (remoteAddress == null) {
            throw new GatewayException("必须有远程地址");
        }
        String clientIp = remoteAddress.getHostString();
        Set<Object> blackListIp = determineToBlackList(clientIp);
        if (!CollectionUtils.isEmpty(blackListIp) && blackListIp.contains(clientIp)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            log.debug("IP:" + clientIp + " 在⿊名单中，将被拒绝访问！ ");
            return WebFluxUtils.webFluxWrite(response, exchange, Result.fail("ip已被列入黑名单"));
        }

        return chain.filter(exchange);
    }

    /**
     * 根据拦截规则决定是否加入黑名单
     *
     * @param clientIp 客户端ip
     */
    private Set<Object>  determineToBlackList(String clientIp) {
        //  考虑加入责任链模式，链尾返回false，不加入黑名单，否则加入
        //  何时加入黑名单已经其规则需要考虑，黑名单可以保存在db中
        Set<Object> blackListIp = redisObjectUtil.sGet(BLACK_LIST_KEY);
        if (true){
            blackListIp.add(clientIp);
            redisObjectUtil.sSetWithTime(BLACK_LIST_KEY, 60L*60L*24L, blackListIp);
        }
        return blackListIp;
    }


    @Override
    public int getOrder() {
        return FilterOrderConstant.BLACKLIST_FILTER_ORDER;
    }

}
