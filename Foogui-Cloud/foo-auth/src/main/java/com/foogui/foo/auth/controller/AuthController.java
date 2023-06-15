package com.foogui.foo.common.web.foo.auth.controller;

import com.foogui.foo.common.web.foo.auth.service.AuthService;
import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.security.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginUser loginUser) {
        return authService.login(loginUser);
    }

    @GetMapping("/logout")
    public Result<String> logout() {
        return authService.logout();
    }

    @PostMapping("/testConfig")
    public Result<LoginUser> testConfig(@RequestBody LoginUser loginUser) {
        return Result.success(null, "testConfig successful");
    }
}
