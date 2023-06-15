package com.foogui.foo.modules.user.service;

import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.mybatis.service.FooService;
import com.foogui.foo.modules.user.domain.SysUserDTO;
import com.foogui.foo.modules.user.domain.SysUserPO;
import com.foogui.foo.modules.user.domain.SysUserSearchCondition;

public interface SysUserService extends FooService<SysUserPO> {
    Object search(SysUserSearchCondition condition);

    Result<SysUserDTO> queryByUsername(String username);
}

