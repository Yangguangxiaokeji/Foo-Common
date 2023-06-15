package com.foogui.foo.common.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableInnerCommunication
@EnableGatewayFilter
@EnableRequestAgainFilter
public @interface EnableCommonFilter {
}
