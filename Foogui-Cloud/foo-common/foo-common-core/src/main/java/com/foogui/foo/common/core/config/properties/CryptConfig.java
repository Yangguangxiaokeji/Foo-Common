package com.foogui.foo.common.core.config.properties;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.Serializable;

@Configuration
@ConfigurationProperties(prefix = "crypto")
@PropertySource("classpath:crypto.properties")
@Data
public class CryptConfig implements Serializable {

    private Mode mode;
    private Padding padding;
    private String key;
    private String iv;

}
