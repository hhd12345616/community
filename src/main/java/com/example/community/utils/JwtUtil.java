package com.example.community.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    //有效期
    public static final Long JWT_TTL=60*60*1000L;
    //设置密钥明文
    public static final String JWT_KEY="hhd";

    public static String getUUID(){
        String token= UUID.randomUUID().toString().replaceAll("-","");
        return token;
    }

    public static String createJWT(String subject){
        JwtBuilder builder = getJwtBuilder(subject,null,getUUID());
        return builder.compact();
    }

    public static String createJWT(String subject,Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject,ttlMillis,getUUID());
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject,Long ttlMillis,String uuid){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis+ttlMillis;
        Date expDate =new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer("hhd")
                .setIssuedAt(now)
                .signWith(signatureAlgorithm,secretKey)
                .setExpiration(expDate);
    }

    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
