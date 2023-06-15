package com.foogui.foo.modules.demo.service;

import com.foogui.foo.common.mybatis.service.FooService;
import com.foogui.foo.modules.demo.domain.DemoPO;
import com.foogui.foo.modules.demo.domain.DemoSearchCondition;

public interface DemoService extends FooService<DemoPO> {
    Object search(DemoSearchCondition condition);
}

