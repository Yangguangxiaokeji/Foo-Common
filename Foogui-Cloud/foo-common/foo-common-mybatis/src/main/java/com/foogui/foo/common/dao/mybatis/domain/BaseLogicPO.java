package com.foogui.foo.common.dao.mybatis.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;

/**
 * 逻辑PO
 * 继承此PO即可实现逻辑删除功能
 * @author Foogui
 * @date 2023/05/11
 */
@Data
public class BaseLogicPO extends BasePO implements Serializable {

    private static final long serialVersionUID = 1030514455302973160L;

    @TableLogic
    @TableField(value = "is_deleted")
    // private int deleted = 0 ;
    private int deleted ;
}
