package com.foogui.foomodules.demo.service;

import com.foogui.foo.common.dao.mybatis.service.FooService;
import com.foogui.foomodules.demo.domain.DemoPO;
import com.foogui.foomodules.demo.domain.DemoSearchCondition;

public interface DemoService extends FooService<DemoPO> {
    Object search(DemoSearchCondition condition);
}

