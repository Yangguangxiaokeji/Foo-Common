package com.foogui.foo.common.web.foo.generator.mapper;

import com.foogui.foo.common.web.foo.generator.domain.Table;

/**
 * 业务 数据层
 *
 * @author ruoyi
 */
public interface TableMapper {
    Table selectRawTableInfo(String tableName);
}