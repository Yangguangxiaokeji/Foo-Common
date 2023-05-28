package com.foogui.foo.common.security.utils;

import com.foogui.foo.common.security.domain.LoginUserDetail;

public class UserHolder {
    private static final ThreadLocal<LoginUserDetail> tl = new ThreadLocal<>();

    public static void saveUser(LoginUserDetail loginUserDetail){
        tl.set(loginUserDetail);
    }

    public static LoginUserDetail getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
