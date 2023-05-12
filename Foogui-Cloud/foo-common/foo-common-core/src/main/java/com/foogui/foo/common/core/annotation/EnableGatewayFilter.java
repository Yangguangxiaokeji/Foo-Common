package com.foogui.foo.common.core.annotation;

import com.foogui.foo.common.core.filter.GatewayFilter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({GatewayFilter.class})
@Inherited
public @interface EnableGatewayFilter {

}
