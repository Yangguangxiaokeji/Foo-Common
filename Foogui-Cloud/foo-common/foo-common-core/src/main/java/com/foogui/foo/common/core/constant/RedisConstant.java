package com.foogui.foo.common.core.constant;

/**
 * @author wangxin
 * @version $ Id: RedisConstant, v 0.1 2023/02/16 13:27 banma-0369 Exp $
 */
public interface RedisConstant {
     final String LOGIN_TOKEN="login:token:";
     final String CAPTCHA_CODE ="captcha:code:";
     final String CAPTCHA_LOGIN ="captcha:login:";
     final String JWT_TOKEN ="jwt_token";

     final Long LOGIN_TTL =30L;


}
