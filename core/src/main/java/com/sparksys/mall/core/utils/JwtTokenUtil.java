package com.sparksys.mall.core.utils;

import com.sparksys.mall.core.constant.CoreConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * 中文类名: JwtToken生成的工具类
 * 中文描述: JwtToken生成的工具类
 *
 * @author zhouxinlei
 * @date 2019-09-11 17:56:53
 */
@Slf4j
public class JwtTokenUtil {

    /**
     * 根据负责生成JWT的token
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, CoreConstant.JwtTokenConstant.JWT_SECRET)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(CoreConstant.JwtTokenConstant.JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    public static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + CoreConstant.JwtTokenConstant.JWT_EXPIRATION * 1000);
    }

    /**
     * 从token中获取登录用户名
     */
    public static String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token 客户端传入的token
     */
    public static boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    public static boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    public static Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public static String generateToken(String userName) {
        Map<String, Object> claims = HashMapUtils.newInstance(2);
        claims.put(CoreConstant.JwtTokenConstant.CLAIM_KEY_USERNAME, userName);
        claims.put(CoreConstant.JwtTokenConstant.CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CoreConstant.JwtTokenConstant.CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
}
