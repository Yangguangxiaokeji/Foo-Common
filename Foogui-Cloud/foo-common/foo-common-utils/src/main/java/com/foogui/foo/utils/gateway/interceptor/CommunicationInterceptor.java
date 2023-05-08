package com.foogui.foo.utils.gateway.interceptor;

import com.foogui.foo.common.core.constant.HttpConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class CommunicationInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // 独立设置参数
        template.header(HttpConstant.FROM_WHERE,HttpConstant.FROM_WHERE_VALUE);
    }
}
