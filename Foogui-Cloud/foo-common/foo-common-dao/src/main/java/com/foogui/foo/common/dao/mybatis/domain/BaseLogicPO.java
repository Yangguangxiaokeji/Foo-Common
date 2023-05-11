package com.foogui.foo.common.dao.mybatis.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseLogicPO extends BasePO implements Serializable {

    private static final long serialVersionUID = 1030514455302973160L;

    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDelete = 0 ;
}
