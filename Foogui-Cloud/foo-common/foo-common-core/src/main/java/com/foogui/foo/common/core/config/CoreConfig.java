package com.foogui.foo.common.web.foo.common.core.config;

import cn.hutool.crypto.symmetric.AES;
import com.foogui.foo.common.web.foo.common.core.config.properties.CryptConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class CoreConfig {
    @Autowired
    CryptConfig cryptConfig;
    @Bean
    public AES aes() {
        return new AES(cryptConfig.getMode(), cryptConfig.getPadding(), cryptConfig.getKey().getBytes(StandardCharsets.UTF_8), cryptConfig.getIv().getBytes(StandardCharsets.UTF_8));
    }
}
