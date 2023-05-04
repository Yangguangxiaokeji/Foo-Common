package com.foogui.foo.common.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页查询条件
 *
 * @author Foogui
 * @date 2023/05/04
 */
@Getter
@Setter
public class PageQuery<T> implements Serializable {

    private static final long serialVersionUID = 2385564691641233828L;

    private int pageNumber = 1;

    private int pageSize = 10;

    /**
     * 动态查询条件
     */
    private T condition;
}
