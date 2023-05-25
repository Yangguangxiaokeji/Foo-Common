package com.foogui.foo.common.dao.mybatis.domain;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FooPage<T> extends PageInfo<T> {
    private static final long serialVersionUID = 2385564691641233828L;
    public FooPage(List<? extends T> list) {
        super(list);
    }
}
