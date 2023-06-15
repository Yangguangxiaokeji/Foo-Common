package com.foogui.foo.modules.log.service;

import com.foogui.foo.common.mybatis.service.FooService;
import com.foogui.foo.modules.log.domain.SysLogPO;
import com.foogui.foo.modules.log.domain.SysLogSearchCondition;

public interface SysLogService extends FooService<SysLogPO> {
    Object search(SysLogSearchCondition condition);


}

