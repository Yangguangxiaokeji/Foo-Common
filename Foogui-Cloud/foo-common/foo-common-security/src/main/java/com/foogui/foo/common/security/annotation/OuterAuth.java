package com.foogui.foo.common.security.annotation;

import com.foogui.foo.common.security.enums.AuthPattern;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OuterAuth {
    /**
     * 需要校验的权限数组
     */
    String[] value() default {};

    /**
     * 验证模式：AND | OR，默认AND
     */

    AuthPattern authPattern() default AuthPattern.AND;
}
