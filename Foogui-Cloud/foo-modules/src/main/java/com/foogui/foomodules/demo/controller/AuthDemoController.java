package com.foogui.foomodules.demo.controller;

import com.foogui.foo.common.core.domain.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthDemoController {

    @GetMapping("/login")
    public Result<String> login(){
        return Result.success("登录成功");
    }
    @GetMapping("/test")
    public Result<String> test(HttpServletRequest req){
        System.out.println(req.getHeader("userId"));
        return Result.success("/auth/test访问成功");
    }

}
