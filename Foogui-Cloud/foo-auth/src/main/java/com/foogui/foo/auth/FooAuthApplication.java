package com.foogui.foo.common.web.foo.auth;

import com.foogui.foo.common.web.annotation.EnableCommonFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.foogui.foo.api")        // 需要扫描到api包才能创建Feign实例
@EnableDiscoveryClient
@EnableCommonFilter
public class FooAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FooAuthApplication.class, args);
    }

}
