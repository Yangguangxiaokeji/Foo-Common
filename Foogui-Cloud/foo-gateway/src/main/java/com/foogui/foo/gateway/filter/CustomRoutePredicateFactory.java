package com.foogui.foo.common.web.foo.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义路由断言工厂
 *  bug：不生效需要解决
 * @author Foogui
 * @date 2023/05/05
 */
@Slf4j
@Component
public class CustomRoutePredicateFactory extends AbstractRoutePredicateFactory<CustomRoutePredicateFactory.Config> {

    public static final String NAME_KEY = "name";

    public static final String AGE_KEY = "age";

    public CustomRoutePredicateFactory() {

        super(Config.class);
        log.info("Loaded RoutePredicateFactory [Custom]");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                if(config.getName().equals("test")){
                    return true;
                }
                return false;
            }


        };
    }

    /**
     * 快捷配置
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("name");
    }

    public static class Config {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
