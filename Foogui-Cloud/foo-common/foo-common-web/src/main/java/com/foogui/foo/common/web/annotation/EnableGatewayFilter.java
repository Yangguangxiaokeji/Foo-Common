package com.foogui.foo.common.web.annotation;

import com.foogui.foo.common.web.filter.ThroughGatewayFilter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ThroughGatewayFilter.class})
@Inherited
public @interface EnableGatewayFilter {

}
