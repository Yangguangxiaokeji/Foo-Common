package com.foogui.foo.common.log.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.foogui.foo.common.core.utils.ServletHelper;
import com.foogui.foo.common.log.anotation.Log;
import com.foogui.foo.common.log.domain.LogPO;
import com.foogui.foo.common.log.enums.Action;
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
        LogPO logPO = new LogPO();
        prepare(logPO);
        logPO.setAction(action);
        logPO.setClassName(className);
        logPO.setMethodName(methodName);
        logPO.setDescription(StringUtils.isNoneBlank(log.value()) ? log.value() : log.description());
        Object result = null;
        Long startTime = System.currentTimeMillis();
        try {
            result = point.proceed();
        } catch (Throwable e) {
            logPO.setException(ExceptionUtils.getRootCauseMessage(e));
            throw new RuntimeException(e.getMessage());
        } finally {
            Long endTime = System.currentTimeMillis();
            // 记录响应时间
            logPO.setDuration(endTime - startTime);
            // 异步记录日志到db
            recordLogAsync(logPO);
        }
        return result;
    }

    private void recordLogAsync(LogPO logPO) {
        try {
            logAsyncTask.recordLog(logPO);
        } catch (Exception e) {
            // 日志记录insert时发生异常
            log.info("日志插入时发生了异常: {}", ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 日志信息预处理
     *
     * @param logPO 日志
     */
    private void prepare(LogPO logPO) {
        HttpServletRequest request = ServletHelper.getRequest();
        logPO.setIp(ServletUtil.getClientIP(request));
        logPO.setUri(URLUtil.getPath(request.getRequestURI()));
        logPO.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
    }
}
