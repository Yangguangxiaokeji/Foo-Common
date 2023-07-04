package com.foogui.foo.generator.mapper;

import com.foogui.foo.generator.domain.Table;

/**
 * 业务 数据层
 *
 * @author ruoyi
 */
public interface TableMapper {
    Table selectRawTableInfo(String tableName);
}