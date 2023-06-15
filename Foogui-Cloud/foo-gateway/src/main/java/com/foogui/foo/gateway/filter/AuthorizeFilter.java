package com.foogui.foo.common.web.foo.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.foogui.foo.common.core.constant.CacheConstant;
import com.foogui.foo.common.core.constant.FilterOrderConstant;
import com.foogui.foo.common.core.constant.HttpConstant;
import com.foogui.foo.common.core.domain.LoginUser;
import com.foogui.foo.common.core.utils.JwtUtil;
import com.foogui.foo.common.web.foo.common.redis.service.RedisObjectUtil;
import com.foogui.foo.common.core.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


/**
 * 鉴权过滤器
 *
 * @author Foogui
 * @date 2023/05/10
 */
@Component
@Slf4j
@ConditionalOnProperty(name = "enable.auth", havingValue = "true", matchIfMissing = true)
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisObjectUtil redisObjectUtil;
    private final String[] skipAuthUrls = {"/auth/login", "/auth/logout", "/auth/register", "/auth/redirect"};

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
        ServerHttpRequest newRequest = verifyToken(request);
        return chain.filter(exchange.mutate().request(newRequest).build());
    }


    /**
     * 验证token是否合法
     * 保存信息到请求头
     *
     * @param
     * @param
     */
    private ServerHttpRequest verifyToken(ServerHttpRequest request) {
        // 将信息存入请求头，方便传递
        // 从请求头中取得token
        String token = request.getHeaders().getFirst(HttpConstant.Authorization);
        // 判断前端是否出携带了token
        if (StringUtils.isBlank(token)) {
            log.info("token不能为空");
            throw new AuthException("token不能为空");
        }
        String uuid = jwtUtil.verifyJwt(token, CacheConstant.LOGIN_TOKEN);
        String userJson = redisObjectUtil.getString(uuid, String.class);
        if (StringUtils.isNoneBlank(uuid,userJson)){
            LoginUser loginUser = JSONUtil.toBean(userJson, LoginUser.class);
            redisObjectUtil.expire(uuid, 30L * 60L);
            // 将信息存入请求头，方便传递
            return request.mutate().header("userId", loginUser.getUserId()).build();
        }else {
            throw new AuthException("鉴权失败");
        }

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
        return FilterOrderConstant.AUTHORIZE_FILTER_ORDER;
    }
}
