package com.foogui.foo.common.core.annotation;

import com.foogui.foo.common.core.interceptor.FeignRequestInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FeignRequestInterceptor.class})
@Inherited
public @interface EnableInnerCommunication {
}
