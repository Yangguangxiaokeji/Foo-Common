package com.foogui.foomodules.demo.service;

import com.foogui.foo.common.dao.mybatis.service.FooService;
import com.foogui.foomodules.demo.dto.DemoPO;
import com.foogui.foomodules.demo.dto.DemoFooSearchVO;

public interface DemoService extends FooService<DemoPO> {
    Object search(DemoFooSearchVO condition);
}

