package com.foogui.foo.generator.domain;

import com.foogui.foo.common.mybatis.domain.BasePO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * 表
 *
 * @author Foogui
 * @date 2023/05/11
 */
@Data
public class Table extends BasePO {

    private static final long serialVersionUID = 1623794836078801057L;


    /**
     * 表名称
     */
    @NotBlank(message = "表名称不能为空")
    private String tableName;

    /**
     * 表描述
     */
    @NotBlank(message = "表描述不能为空")
    private String tableComment;


    /**
     * 实体类名称(首字母大写)
     */
    @NotBlank(message = "实体类名称不能为空")
    private String className;

    /**
     * 生成包路径
     */
    @NotBlank(message = "生成包路径不能为空")
    private String packagePath;

    /**
     * 生成模块名
     */
    @NotBlank(message = "生成模块名不能为空")
    private String moduleName;

    /**
     * 生成业务名
     */
    @NotBlank(message = "生成业务名不能为空")
    private String businessName;

    /**
     * 生成作者
     */
    @NotBlank(message = "作者不能为空")
    private String author;

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    private String genType;

    /**
     * 生成路径（不填默认项目路径）
     */
    private String genPath;

    /**
     * 主键信息
     */
    private Column pk;

    /**
     * 方法功能说明
     */
    private String functionName;


    /**
     * 表列信息
     */
    @Valid
    private List<Column> columns;


}
