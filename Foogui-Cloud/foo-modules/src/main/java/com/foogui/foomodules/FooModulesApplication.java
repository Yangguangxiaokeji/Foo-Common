package com.foogui.foomodules;

import com.foogui.foo.common.core.annotation.EnableCommonFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableCommonFilter
@MapperScan("com.foogui.foomodules.demo.dao")
@EnableAspectJAutoProxy
public class FooModulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FooModulesApplication.class, args);
    }

}
