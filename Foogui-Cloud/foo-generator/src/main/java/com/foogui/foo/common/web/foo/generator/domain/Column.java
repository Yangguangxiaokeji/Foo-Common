package com.foogui.foo.common.web.foo.generator.domain;

import com.foogui.foo.common.mybatis.domain.BasePO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 代码生成业务字段表
 *
 * @author ruoyi
 */
@Data
public class Column extends BasePO {

    private static final long serialVersionUID = 898085250093922131L;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    @NotBlank(message = "Java属性不能为空")
    private String javaField;

    /**
     * 是否主键（1是）
     */
    private String isPk;

}