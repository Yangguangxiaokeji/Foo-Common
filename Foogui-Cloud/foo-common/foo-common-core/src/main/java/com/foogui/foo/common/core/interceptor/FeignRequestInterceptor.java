package com.foogui.foo.common.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.foogui.foo.common.core.constant.HttpConstant;
import com.google.common.collect.Maps;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 微服务调用，将用户信息和请求来源存入header中
 *
 * @author Foogui
 * @date 2023/05/25
 */
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        //todo：从context获取用户信息存入
        // 模拟
        Map<String, String> user= Maps.newHashMap();
        if(user != null){
            try {
                String userJson = JSON.toJSONString(user) ;
                template.header(HttpConstant.USER_INFO,  URLEncoder.encode(userJson, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        template.header(HttpConstant.FROM_WHERE, HttpConstant.INNER);
    }
}
