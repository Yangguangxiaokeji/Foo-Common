package com.foogui.foo.common.dao.mybatis.domain;

import com.github.pagehelper.PageInfo;

import java.util.List;


public class FooPage<T> extends PageInfo<T> {


    public FooPage(List<? extends T> list) {
        super(list);
    }
}
