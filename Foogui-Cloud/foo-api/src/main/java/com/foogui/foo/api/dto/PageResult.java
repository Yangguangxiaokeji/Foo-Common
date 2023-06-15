package com.foogui.foo.common.web.foo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {


    private static final long serialVersionUID = -6775222620549704145L;
    /**
     * 当前页面
     */
    private int pageNumber;
    /**
     * 单页数
     */
    private int pageSize;
    /**
     * 总共的数量
     */
    private int total;
    /**
     * 数据
     */
    private List<T> list;
    /**
     * 扩展
     */
    private Object extend;

}
