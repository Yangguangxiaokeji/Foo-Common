package com.foogui.foo.common.web.foo.api.fallback;

import com.foogui.foo.common.web.foo.api.dto.SysUserDTO;
import com.foogui.foo.common.web.foo.api.service.FeignSysUserService;
import com.foogui.foo.common.core.domain.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SysUserFallbackFactory implements FeignSysUserService {


    @Override
    public Result<SysUserDTO> queryByUsername(String username) {
        log.info("queryByUsername超时，返回null");
        return Result.success(null);
    }
}
