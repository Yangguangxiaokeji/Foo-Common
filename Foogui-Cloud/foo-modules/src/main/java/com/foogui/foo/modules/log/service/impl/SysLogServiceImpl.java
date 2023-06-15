package com.foogui.foo.common.web.foo.modules.log.service.impl;

import com.foogui.foo.common.mybatis.domain.FooPage;
import com.foogui.foo.common.mybatis.service.FooBaseServiceImpl;
import com.foogui.foo.common.web.foo.modules.log.service.SysLogService;
import com.foogui.foo.common.web.foo.modules.log.dao.SysLogMapper;
import com.foogui.foo.common.web.foo.modules.log.domain.SysLogPO;
import com.foogui.foo.common.web.foo.modules.log.domain.SysLogSearchCondition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLogServiceImpl extends FooBaseServiceImpl<SysLogMapper, SysLogPO> implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public FooPage<SysLogPO> search(SysLogSearchCondition condition) {
        startPage(condition);
        return getPage(condition);
    }

}
