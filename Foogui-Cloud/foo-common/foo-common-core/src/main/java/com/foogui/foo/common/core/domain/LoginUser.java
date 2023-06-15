package com.foogui.foo.common.web.foo.common.core.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -3276071923234021046L;

    private String userId;
    private String username;
}
