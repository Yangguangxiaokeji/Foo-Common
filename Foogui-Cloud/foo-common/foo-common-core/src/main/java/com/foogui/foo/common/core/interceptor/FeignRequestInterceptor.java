package com.foogui.foo.common.core.interceptor;

import com.foogui.foo.common.core.constant.HttpConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        template.header(HttpConstant.FROM_WHERE, HttpConstant.INNER);
    }
}
