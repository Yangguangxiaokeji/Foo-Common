package com.foogui.foo.common.security.domain;

import com.foogui.foo.common.dao.mybatis.domain.BasePO;
import lombok.Data;

/**
 * 用户信息
 *
 * @author Foogui
 * @date 2023/05/24
 */
@Data
public class UserInfo extends BasePO {

    private String username;

    private String password;

    private String phone;

}
