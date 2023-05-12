package com.foogui.foo.common.core.annotation;

import com.foogui.foo.common.core.interceptor.CommunicationInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({CommunicationInterceptor.class})
@Inherited
public @interface EnableInnerCommunication {
}
