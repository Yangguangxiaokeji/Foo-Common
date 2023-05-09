package com.foogui.foo.gateway.filter;

import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.gateway.utils.WebFluxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
@Slf4j
public class BlackListGatewayFilterFactory extends AbstractGatewayFilterFactory<BlackListGatewayFilterFactory.Config> {


    public BlackListGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(BlackListGatewayFilterFactory.Config config) {
        System.out.println("执行了BlackListFilter");
        return (exchange, chain) -> {
            String url = exchange.getRequest().getURI().getPath();
            if (config.checkUrl(url, exchange)) {
                WebFluxUtils.webFluxWrite(exchange.getResponse(), exchange, Result.fail("路径在黑名单中，无法访问"));
            }
            return chain.filter(exchange);
        };
    }


    public static class Config {
        private List<String> blacklist;

        public List<String> getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(List<String> blacklist) {
            this.blacklist = blacklist;
        }


        /**
         * 检查url是否包含在黑名单中
         *
         * @param url url
         */
        public boolean checkUrl(String url, ServerWebExchange exchange) {
            boolean flag = false;
            if (!CollectionUtils.isEmpty(blacklist)) {
                flag = blacklist.stream().anyMatch(url::matches);
            }
            return flag;
        }
    }
}
