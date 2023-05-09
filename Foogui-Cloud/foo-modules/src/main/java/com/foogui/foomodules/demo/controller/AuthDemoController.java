package com.foogui.foomodules.demo.controller;

import com.foogui.foo.common.core.domain.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthDemoController {

    @GetMapping("/login")
    public Result<String> login(){
        return Result.success("登录成功");
    }

}
