package com.suai.library;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen(){
        Map<String,Object> claims= new HashMap<>();
        claims.put("id",1);
        claims.put("username","张宇航");
        //生成jwt代码
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))//添加过期时间
                .sign(Algorithm.HMAC256("suai"));//指定密钥

        System.out.println(token);
    }

    @Test
    public void testParse(){

        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOWuh-iIqiJ9LCJleHAiOjE3NDk4MTc5NDN9" +
                ".gg_aFHba38HVnTqtHq5CXxlJIULU8DTzMxGdOg3nDE8";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("suai")).build();

        DecodedJWT verify = jwtVerifier.verify(token);

        Map<String, Claim> claims= verify.getClaims();

        System.out.println(claims.get("user"));
    }
}
