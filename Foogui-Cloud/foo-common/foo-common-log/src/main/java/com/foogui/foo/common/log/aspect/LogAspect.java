package com.foogui.foo.common.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.foogui.foo.api.dto.SysLogDTO;
import com.foogui.foo.common.core.enums.Action;
import com.foogui.foo.common.core.utils.ServletHelper;
import com.foogui.foo.common.log.anotation.Log;
import com.foogui.foo.common.log.task.LogAsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Slf4j
public class LogAspect {


    @Autowired
    private LogAsyncTask logAsyncTask;

    /**
     * 约束所切注解的位置
     */
    @Pointcut("@annotation(com.foogui.foo.common.log.anotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut() && @annotation(log)")
    public Object around(ProceedingJoinPoint point, Log log) {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Action action = log.action();
        SysLogDTO sysLogDTO = new SysLogDTO();

        prepare(sysLogDTO);
        sysLogDTO.setAction(action);
        sysLogDTO.setClassName(className);
        sysLogDTO.setMethodName(methodName);
        sysLogDTO.setDescription(StringUtils.isNoneBlank(log.value()) ? log.value() : log.description());
        Object result = null;
        Long startTime = System.currentTimeMillis();
        try {
            result = point.proceed();
        } catch (Throwable e) {
            sysLogDTO.setException(ExceptionUtils.getRootCauseMessage(e));
            throw new RuntimeException(e.getMessage());
        } finally {
            Long endTime = System.currentTimeMillis();
            // 记录响应时间
            sysLogDTO.setDuration(endTime - startTime);
            // 异步记录日志到db
            recordLogAsync(sysLogDTO);
        }
        return result;
    }

    private void recordLogAsync(SysLogDTO sysLogDTO) {
        try {
            logAsyncTask.recordLog(sysLogDTO);
        } catch (Exception e) {
            // 日志记录insert时发生异常
            log.info("日志插入时发生了异常: {}", ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 日志信息预处理
     *
     * @param sysLogDTO 日志
     */
    private void prepare(SysLogDTO sysLogDTO) {
        HttpServletRequest request = ServletHelper.getRequest();
        sysLogDTO.setIp(ServletUtil.getClientIP(request));
        sysLogDTO.setUri(URLUtil.getPath(request.getRequestURI()));
        sysLogDTO.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
    }
}
