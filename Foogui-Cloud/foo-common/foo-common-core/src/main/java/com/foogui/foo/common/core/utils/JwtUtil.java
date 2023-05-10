package com.foogui.foo.common.core.utils;

import cn.hutool.crypto.asymmetric.RSA;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.foogui.foo.common.core.constant.MessageStoreConstant;
import com.foogui.foo.common.core.constant.RedisConstant;
import com.foogui.foo.common.core.exception.AuthException;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Map;

@Component
public class JwtUtil {

    private String privateKey= MessageStoreConstant.PRIVATE_KEY;

    private String publicKey=MessageStoreConstant.PUBLIC_KEY;


    public String createJwtToken(Map<String, String> payload, int calendarField, int amount){
        //设置过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendarField, amount);
        //创建builder
        JWTCreator.Builder builder = JWT.create();
        //设置载荷
        payload.forEach(builder::withClaim);
        //根据私钥创建RSA对象
        RSA rsa = new RSA(privateKey, null);
        //获取私钥对象
        RSAPrivateKey privateKey = (RSAPrivateKey)rsa.getPrivateKey();
        //根据私钥对象获取jwt
        return builder.withExpiresAt(calendar.getTime()).sign(Algorithm.RSA256(null,privateKey));
    }

    /**
     * @param token
     * @return loginKey
     */
    public String verifierJwtToken(String token){
        //根据公钥创建RSA对象
        RSA rsa = new RSA(null, publicKey);
        //获取公钥对象
        RSAPublicKey publicKey = (RSAPublicKey)rsa.getPublicKey();
        //根据公钥对象获取解析对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.RSA256(publicKey, null)).build();
        try{
            //解析token
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return decodedJWT.getClaim(RedisConstant.LOGIN_TOKEN).asString();
        }catch (TokenExpiredException e){
            throw new AuthException("token过期");
        }catch (SignatureVerificationException e){
            throw new AuthException("无效签名");
        }catch (AlgorithmMismatchException e){
            throw new AuthException("算法不一致");
        } catch (Exception e){
            throw new AuthException("token无效");
        }
    }

}
