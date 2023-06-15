package com.foogui.foo.common.web.foo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FooGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FooGatewayApplication.class, args);
    }

}
