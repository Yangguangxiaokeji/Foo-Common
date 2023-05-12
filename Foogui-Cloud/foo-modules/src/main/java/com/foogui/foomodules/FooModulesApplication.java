package com.foogui.foomodules;

import com.foogui.foo.common.core.annotation.EnableOnlyFromGateway;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableOnlyFromGateway          // 开启服务必须通过网关的功能
@MapperScan("com.foogui.foomodules.demo.dao")
public class FooModulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FooModulesApplication.class, args);
    }

}
