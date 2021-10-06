package com.tyzz.blog.util;

import com.tyzz.blog.entity.User;
import com.tyzz.blog.exception.BlogLoginInvalidException;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: jwt工具类
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 12:30
 */
public class JwtUtils {
    private final static String PRIVATE_KEY = "tyzz";
    /*过期持续时间为1天*/
    private final static long EXPIRE_TIME_DURATION = 86400000L;

    public static String buildToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("username", user.getUsername());
        return encode(claims);
    }

    public static User checkToken(String token) {
        try {
            Claims claims = decode(token);
            return User.builder()
                        .userId(Long.parseLong(claims.get("userId").toString()))
                        .username(claims.get("username").toString())
                        .build();
        } catch (Exception e) {
            throw new BlogLoginInvalidException("登录信息异常");
        }
    }

    public static String encode(Map<String, Object> claims) {
        HashMap<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME_DURATION))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)
                .compact();
    }

    public static Claims decode(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(PRIVATE_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            throw new BlogLoginInvalidException("登录状态失效");
        } catch (UnsupportedJwtException e) {
            throw new BlogLoginInvalidException("登陆状态非法");
        }
    }
}
