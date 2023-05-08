package com.foogui.foo.utils.gateway.annotation;

import com.foogui.foo.utils.gateway.filter.GatewayFilter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({GatewayFilter.class})
@Inherited
public @interface EnableGatewayFilter {

}
