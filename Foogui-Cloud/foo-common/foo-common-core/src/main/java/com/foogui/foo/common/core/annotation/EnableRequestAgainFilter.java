package com.foogui.foo.common.core.annotation;

import com.foogui.foo.common.core.filter.RequestAgainFilter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启request可以多次读取流的功能
 *
 * @author Foogui
 * @date 2023/05/12
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RequestAgainFilter.class})
@Inherited
public @interface EnableRequestAgainFilter {

}
