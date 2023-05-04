package com.foogui.foo.common.log.anotation;

import com.foogui.foo.common.log.enums.Action;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 日志描述
     * 同时设置value和 description以value为准
     * @return {@link String}
     */
    @AliasFor("description")
    String value() default "";

    /**
     * 日志描述
     *
     * @return {@link String}
     */
    @AliasFor("value")
    String description() default "";

    /**
     * 行为类型
     *
     * @return {@link Action}
     */
    Action action() default Action.DEFAULT;


}
