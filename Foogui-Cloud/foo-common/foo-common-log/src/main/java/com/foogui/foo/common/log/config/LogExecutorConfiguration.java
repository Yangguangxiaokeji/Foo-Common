package com.foogui.foo.common.log.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Configuration
@ConditionalOnProperty(name="enable.log", havingValue="true", matchIfMissing = true)
public class LogExecutorConfiguration implements AsyncConfigurer {
    @Autowired
    private ConfigProperties configProperties;

    @Bean(name = "logExecutor")
    public ThreadPoolTaskExecutor executor() {
        // ThreadPoolTaskExecutor ：最常使用，推荐。其实质是对java.util.concurrent.ThreadPoolExecutor的包装
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(configProperties.getCorePoolSize());
        // 线程池维护线程的最大数量,只有在缓冲队列满了之后才会申请超过核心线程数的线程
        taskExecutor.setMaxPoolSize(configProperties.getMaxPoolSize());
        // 缓存队列
        taskExecutor.setQueueCapacity(configProperties.getQueueCapacity());
        // 许的空闲时间,当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        taskExecutor.setKeepAliveSeconds(200);
        // 异步方法内部线程名称
        taskExecutor.setThreadNamePrefix(configProperties.getNamePrefix());
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) ->
                log.error("线程池执行任务发送未知错误,执行方法：{}", method.getName(), ex);
    }
}
