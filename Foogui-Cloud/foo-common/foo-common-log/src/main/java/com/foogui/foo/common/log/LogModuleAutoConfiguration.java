package com.foogui.foo.common.log;

import com.foogui.foo.common.log.aspect.LogAspect;
import com.foogui.foo.common.log.task.LogAsyncTask;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 日志模块自动配置类
 * 用于扫描加载log模块对应的Bean交给Spring管理
 * @author Foogui
 * @date 2023/05/04
 */
@EnableAsync
@ConditionalOnProperty(name="enable.log", havingValue="true", matchIfMissing = true)
@Configuration
public class LogModuleAutoConfiguration {
    @Bean
    public LogAsyncTask logAsyncTask() {
        return new LogAsyncTask();
    }

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
