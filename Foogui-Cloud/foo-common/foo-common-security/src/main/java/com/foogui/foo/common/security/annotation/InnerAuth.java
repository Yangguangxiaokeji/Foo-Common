package com.foogui.foo.common.security.annotation;

import java.lang.annotation.*;

/**
 * 内部身份标记
 *
 * @author Foogui
 * @date 2023/05/05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerAuth {
}
