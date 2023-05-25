package com.foogui.foo.common.dao.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foogui.foo.common.dao.mybatis.domain.BaseSearchDTO;
import com.foogui.foo.common.dao.mybatis.domain.SearchCondition;
import com.foogui.foo.common.dao.mybatis.domain.FooPage;

public interface FooService<T> extends IService<T> {

    /**
     * 开启分页功能
     *
     * @param condition 条件
     */
    void startPage(BaseSearchDTO condition);

    FooPage<T> getPage(SearchCondition condition);

}
