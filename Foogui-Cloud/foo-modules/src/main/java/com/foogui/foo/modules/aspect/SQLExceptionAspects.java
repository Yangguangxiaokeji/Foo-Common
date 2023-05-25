package com.foogui.foo.modules.aspect;

import com.foogui.foo.common.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class SQLExceptionAspects {


    @Pointcut("execution( * com.foogui.foomodules.*.controller.*.*(..))")
    public void  pointCut() {
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void dealException(DataAccessException exception){
        log.error(exception.getMessage(), exception);
        throw new BizException("数据库操作异常，请联系平台开发同学协助排查！");
    }
}
