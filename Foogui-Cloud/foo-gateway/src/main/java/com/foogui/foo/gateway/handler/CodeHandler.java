package com.foogui.foo.gateway.handler;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.UUID;
import com.foogui.foo.common.core.constant.CacheConstant;
import com.foogui.foo.common.dao.redis.RedisStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * 用于生成登录时的验证码
 *
 * @author Foogui
 * @date 2023/05/09
 */
@Component
public class CodeHandler implements HandlerFunction<ServerResponse> {

    @Autowired
    private RedisStringUtil redisStringUtil;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        // 自定义纯数字的验证码（随机4位数字，可重复）
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);
        // 重新生成code
        lineCaptcha.createCode();
        String codeKey=request.headers().firstHeader("codeKey");
        // 刷新token
        if (StringUtils.isNotBlank(codeKey) && redisStringUtil.hasKey(codeKey)){
            redisStringUtil.setEx(codeKey,lineCaptcha.getCode(),60L, TimeUnit.SECONDS);
        }else{
            codeKey = CacheConstant.CAPTCHA_LOGIN + UUID.fastUUID().toString(true);
            redisStringUtil.setEx(codeKey,lineCaptcha.getCode(),60L, TimeUnit.SECONDS);
        }


        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        lineCaptcha.write(os);
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .header("codeKey", codeKey)
                .body(BodyInserters.fromResource(new ByteArrayResource(os.toByteArray())));
    }
}
