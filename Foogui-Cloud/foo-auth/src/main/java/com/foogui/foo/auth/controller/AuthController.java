package com.foogui.foo.auth.controller;

import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.security.domain.UserInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/login")
    public Result<UserInfo> login(@RequestBody UserInfo userInfo) {
        return Result.success(null, "Login successful");
    }

    @PostMapping("/testConfig")
    public Result<UserInfo> testConfig(@RequestBody UserInfo userInfo) {
        return Result.success(null, "testConfig successful");
    }
}
