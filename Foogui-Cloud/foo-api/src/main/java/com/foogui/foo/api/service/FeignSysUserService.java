package com.foogui.foo.api.service;

import com.foogui.foo.api.config.FeignConfig;
import com.foogui.foo.api.dto.SysUserDTO;
import com.foogui.foo.api.fallback.SysUserFallbackFactory;
import com.foogui.foo.common.core.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务RPC
 * 当有多个remoteService的注解FeignClient的value都是一个服务名时，需要加contextId来区别Bean
 * @author Foogui
 * @date 2023/05/25
 */
@FeignClient(value = "foo-modules",contextId = "user", configuration = FeignConfig.class, fallbackFactory = SysUserFallbackFactory.class)
public interface FeignSysUserService {
    @PostMapping("/sys-user/queryByUsername")
    public Result<SysUserDTO> queryByUsername(@RequestBody SysUserDTO sysUserDTO);
}
