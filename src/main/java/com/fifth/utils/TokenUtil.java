package com.fifth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    /**
     * 签名秘钥(唯一秘钥，可以用密码做为秘钥)
     */
    public static final String SECRET="fifth";

    /**
     * 生成token
     * @param userId
     * @param userName
     * @return
     */
    public static String createJwtToken(String userId,String userName){
        String issuer="llw";
        String subject="lin";
        long ttlMillis=36000000;//10个小时后过期
        return createJwtToken(userId,userName,issuer,subject,ttlMillis);
    }

    /**
     * 生成token
     * @param username 用户名
     * @param issuer 改JWT的签发者，是否使用可以选
     * @param subject 改JWT所面向的用户，是否使用可选
     * @param ttlMillis 签发时间（有效时间，过期会报错）
     * @return token string
     */
    public static String createJwtToken(String userId, String username,String issuer,String subject,long ttlMillis){
        //签名算法，将token进行签名
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
        //生成签发时间
        long nowMills=System.currentTimeMillis();
        Date now=new Date(nowMills);
        //通过秘钥签名JWT
        byte[] apiKeySecretBytes= DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey=new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());
        Map<String,Object> m = new HashMap<>();
        m.put("userId",userId);
        m.put("userName",username);
        //创建token
        JwtBuilder builder=Jwts.builder().setClaims(m)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm,signingKey);
        //添加过期时间
        if(ttlMillis>=0){
            long expMillis=nowMills+ttlMillis;
            Date exp=new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }
    //验证和读取JWT的示例方法
    public static Claims parseJWT(String jwt){
        Claims claims= Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
    public static boolean verify(String jwt){
        try{
            Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                    .parseClaimsJws(jwt).getBody();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static void main(String[] args){
        String token = TokenUtil.createJwtToken("20191001","llw");
        System.out.println(token);
        Claims claims = parseJWT(token);
        System.out.println(claims.get("userId"));
        System.out.println(claims.get("userName"));
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getExpiration());
        String toke = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IjEyMzQ1NiIsImV4cCI6MTYzNjY2NjY2NiwidXNlcklkIjoiMjAxOTEwMDEiLCJpYXQiOjE2MzY2MzA2NjZ9.6XaiRiFWmQt46RfJEfYztHS-fux8V3T6nwpUYSdx7bM";
        System.out.println("userId:"+TokenUtil.parseJWT(toke).get("userId"));
        System.out.println("userName:"+TokenUtil.parseJWT(toke).get("userName"));
    }

}
