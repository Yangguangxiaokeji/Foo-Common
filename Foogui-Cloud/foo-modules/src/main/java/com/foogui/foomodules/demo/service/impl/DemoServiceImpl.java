package com.foogui.foomodules.demo.service.impl;

import com.foogui.foo.common.dao.mybatis.domain.FooPage;
import com.foogui.foo.common.dao.mybatis.service.FooBaseServiceImpl;
import com.foogui.foomodules.demo.dao.DemoMapper;
import com.foogui.foomodules.demo.domain.DemoPO;
import com.foogui.foomodules.demo.domain.DemoSearchCondition;
import com.foogui.foomodules.demo.service.DemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DemoServiceImpl extends FooBaseServiceImpl<DemoMapper, DemoPO> implements DemoService {

    @Resource
    private DemoMapper demoMapper;

    @Override
    public FooPage<DemoPO> search(DemoSearchCondition condition) {
        startPage(condition);
        return getPage(condition);
    }

}