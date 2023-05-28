package com.foogui.foo.auth.service;

import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.security.domain.LoginUser;

public interface AuthService {

    Result<String> login(LoginUser loginUser);

    Result<String> logout();
}
