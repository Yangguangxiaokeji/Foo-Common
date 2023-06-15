package com.foogui.foo.common.web.annotation;

import com.foogui.foo.common.web.feign.FeignRequestInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FeignRequestInterceptor.class})
@Inherited
public @interface EnableInnerCommunication {
}
