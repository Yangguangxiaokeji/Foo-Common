package com.foogui.foo.auth.controller;

import com.foogui.foo.api.dto.SysUserDTO;
import com.foogui.foo.api.service.FeignSysUserService;
import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.security.domain.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private FeignSysUserService feignSysUserService;

    @PostMapping("/login")
    public Result<UserInfo> login(@RequestBody UserInfo userInfo) {
        SysUserDTO dto = new SysUserDTO();
        dto.setUsername(userInfo.getUsername());
        Result<SysUserDTO> result = feignSysUserService.queryByUsername(dto);
        Result<UserInfo> success = Result.success();
        BeanUtils.copyProperties(result,success);
        return success;
    }

    @PostMapping("/testConfig")
    public Result<UserInfo> testConfig(@RequestBody UserInfo userInfo) {
        return Result.success(null, "testConfig successful");
    }
}
