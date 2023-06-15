package com.foogui.foo.common.web.foo.common.log.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "async.executor.thread")
@PropertySource(value = "classpath:executor.properties")
@Getter
@Setter
@ConditionalOnProperty(name="enable.log", havingValue="true", matchIfMissing = true)
public class ConfigProperties {
    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
    private String namePrefix;
}
