package com.foogui.foo.common.dao.mybatis.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页查询的结果
 *
 * @author Foogui
 * @date 2023/05/04
 */
@Getter
@Setter
public class PageResult<T> implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5074000774435553698L;

    /**
     * 当前页号
     */
    private Integer pageNumber;

    /**
     * 每页行数
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long count;

    /**
     * 查询的内容
     */
    private T records;

}
