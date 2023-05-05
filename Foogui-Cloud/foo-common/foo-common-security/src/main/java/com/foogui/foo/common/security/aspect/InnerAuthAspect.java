package com.foogui.foo.common.security.aspect;

import com.foogui.foo.common.core.constant.HttpConstant;
import com.foogui.foo.common.core.exception.AuthException;
import com.foogui.foo.common.security.annotation.InnerAuth;
import com.foogui.foo.utils.http.ServletHelper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 内部服务调用鉴权处理
 *
 * @author Foogui
 * @date 2023/05/05
 */
@Aspect
@Component
public class InnerAuthAspect {

    @Around("@annotation(innerAuth)")
    public Object innerAround(ProceedingJoinPoint point, InnerAuth innerAuth) throws Throwable {
        HttpServletRequest request = ServletHelper.getRequest();
        String header = request.getHeader(HttpConstant.REQUEST_SOURCE);
        if(StringUtils.isBlank(header)){
            throw new AuthException("内部调用访问必须携带请求头:"+HttpConstant.REQUEST_SOURCE);
        }
        if(!StringUtils.equalsIgnoreCase(header, HttpConstant.INNER)){
            throw new AuthException("内部调用访问必须携带请求头的值必须为:"+HttpConstant.INNER);
        }
        return point.proceed();
    }
}
