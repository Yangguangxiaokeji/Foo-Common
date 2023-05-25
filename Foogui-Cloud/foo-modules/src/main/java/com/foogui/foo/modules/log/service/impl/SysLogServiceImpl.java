package com.foogui.foo.modules.log.service.impl;

import com.foogui.foo.common.dao.mybatis.domain.FooPage;
import com.foogui.foo.common.dao.mybatis.service.FooBaseServiceImpl;
import com.foogui.foo.modules.log.dao.SysLogMapper;
import com.foogui.foo.modules.log.domain.SysLogPO;
import com.foogui.foo.modules.log.domain.SysLogSearchCondition;
import com.foogui.foo.modules.log.service.SysLogService;
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
