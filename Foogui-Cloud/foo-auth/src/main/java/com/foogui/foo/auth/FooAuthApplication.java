package com.foogui.foo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FooAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FooAuthApplication.class, args);
    }

}
