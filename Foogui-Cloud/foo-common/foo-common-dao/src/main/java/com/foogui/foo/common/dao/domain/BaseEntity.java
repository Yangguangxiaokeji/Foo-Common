package com.foogui.foo.common.dao.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PO基类
 *
 * @author Foogui
 * @date 2023/05/02
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 6983027423601649332L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建者
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    /**
     * 版本号
     */
    @TableField(value = "version",  fill = FieldFill.INSERT)
    @Version
    private Integer version;
}
