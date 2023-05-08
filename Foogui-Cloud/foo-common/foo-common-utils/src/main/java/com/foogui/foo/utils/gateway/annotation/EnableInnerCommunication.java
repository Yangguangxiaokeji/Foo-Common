package com.foogui.foo.utils.gateway.annotation;

import com.foogui.foo.utils.gateway.interceptor.CommunicationInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({CommunicationInterceptor.class})
@Inherited
public @interface EnableInnerCommunication {
}
