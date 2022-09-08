package com.study.liu.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    /** 密匙*/
    private static final String SING="liuyishou@jwt6666";
    /** 超时时间：单位分钟*/
    private static final Integer TimeOut=30;

    //生成令牌
    public static String getToken1(Map<String,String> map){
        //获取日历对象
        Calendar calendar=Calendar.getInstance();
        //设置过期时间
        calendar.add(Calendar.MINUTE,TimeOut);
        //新建一个JWT的Builder对象
        JWTCreator.Builder builder = JWT.create();
        //将map集合中的数据设置进payload
        map.forEach((k,v)->{
            builder.withClaim(k, v);
        });
        //设置过期时间和签名
        String sign = builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SING));
        return sign;
    }
    //生成令牌
    public static String getToken(Map<String, Object> map) {
        Map<String, String> newMap =new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            newMap.put(entry.getKey(),entry.getValue()==null?"":entry.getValue().toString());
        }
        String token = getToken1(newMap);
        return token;
    }

    /**
     * 验签并返回DecodedJWT
     * @param token  令牌
     */
    public  static DecodedJWT getTokenInfo(String token){
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }


    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("username","00000");
        map.put("password","2.0");
        String token = getToken1(map);
        System.out.println(token);

        token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjIuMCIsImV4cCI6MTY2MjYwNDI2OSwidXNlcm5hbWUiOiIwMDAwMCJ9.oFVPCS9gTfohPnQ6PRiqZYepAOpxh-MFSDOiA9UbeZ0";
        try {
            DecodedJWT tokenInfo = getTokenInfo(token);
        } catch (Exception e){
            System.out.println(e);
        }
        DecodedJWT tokenInfo = getTokenInfo(token);
        // String username = tokenInfo.getClaim("username").asString();
        String username = tokenInfo.getClaims().get("username").asString();
        System.out.println(username);

    }


}


