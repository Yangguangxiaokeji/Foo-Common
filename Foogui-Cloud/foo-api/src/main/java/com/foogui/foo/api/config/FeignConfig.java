package com.foogui.foo.api.config;


import com.foogui.foo.common.core.constant.HttpConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 内部feign调用添加头信息
 * 微服务的过滤器会对请求拦截，进行头信息判断
 * @author Foogui
 * @date 2023/05/12
 */
public class FeignConfig  implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header(HttpConstant.FROM_WHERE, HttpConstant.INNER);
    }
}
