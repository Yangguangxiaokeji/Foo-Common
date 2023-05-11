package com.foogui.foo.common.dao.mybatis.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FooSearchVO<T> extends BasePage{
    /**
     * 动态查询条件
     */
    private T context;

}
