package com.foogui.foomodules;

import com.foogui.foo.utils.gateway.annotation.EnableOnlyFromGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableOnlyFromGateway      // 开启服务必须通过网关的功能
public class FooModulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FooModulesApplication.class, args);
    }

}
