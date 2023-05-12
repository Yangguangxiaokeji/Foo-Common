package com.foogui.foo.common.dao.mybatis.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页模型
 *
 * @author Foogui
 * @date 2023/05/04
 */
@Getter
@Setter
public class BaseSearchVO implements Serializable {

    private static final long serialVersionUID = 2385564691641233828L;

    private int pageNumber = 1;

    private int pageSize = 10;

    private String orderBy;


}
