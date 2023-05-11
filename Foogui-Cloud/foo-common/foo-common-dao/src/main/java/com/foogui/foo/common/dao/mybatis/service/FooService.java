package com.foogui.foo.common.dao.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foogui.foo.common.dao.mybatis.domain.BasePage;
import com.foogui.foo.common.dao.mybatis.domain.FooSearchVO;
import com.foogui.foo.common.dao.mybatis.domain.FooPage;

public interface FooService<T> extends IService<T> {

    void startPage(BasePage condition);

    FooPage<T> getPage(FooSearchVO condition);

}
