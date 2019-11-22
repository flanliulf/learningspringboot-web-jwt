package com.fancyliu.learningspringboot.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * <p>jwt token工具类</p>
 * <pre>
 *     jwt Payload 中的 claim 里一般包含以下几种数据:
 *         1. iss -- jwt 的签发者
 *         2. sub -- 该 jwt 所面向的用户
 *         3. aud -- 接收该 jwt 的一方
 *         4. exp -- jwt 的过期时间,这个过期时间必须要大于签发时间
 *         5. nbf -- 定义在什么时间之前，该jwt都是不可用的
 *         6. iat -- jwt 的签发时间
 *         7. jti -- jwt 的唯一身份标识,主要用来作为一次性 token, 防止重复使用,从而回避重放攻击。
 * </pre>
 *
 * @author liufan
 * @Date 2017/8/25 10:59
 */
public class JwtTokenUtil {

    /**
     * jwt的秘钥
     */
    private String jwtSecret;

    /**
     * 默认jwt的过期时间
     */
    private Long defaultExpiredDate;

    public JwtTokenUtil(String jwtSecret, Long defaultExpiredDate) {
        this.jwtSecret = jwtSecret;
        this.defaultExpiredDate = defaultExpiredDate;
    }

    /**
     * 从 jwt 中获取用户 id
     *
     * @param token
     * @return
     */
    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token).getSubject();
    }

    /**
     *
     */
    /**
     * 获取 jwt 的发布时间
     *
     * @param token
     * @return
     */
    public Date getIssuedDateFromToken(String token) {
        return getClaimFromToken(token).getIssuedAt();
    }

    /**
     * 获取 jwt 的失效时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }

    /**
     * 获取 jwt 的接收者
     *
     * @param token
     * @return
     */
    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token).getAudience();
    }

    /**
     * 获取 jwt 的 payload 的私有声明部分
     *
     * @param token jwt token
     * @param key   私有声明的 key
     * @return
     */
    public String getPrivateClaimFromToken(String token, String key) {
        return getClaimFromToken(token).get(key).toString();
    }

    /**
     * 获取 jwt 的 payload 的标准声明部分
     *
     * @param token jwt token
     * @return
     */
    public Claims getClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证 token 是否正确
     *
     * @param token
     * @return true-正确, false-错误
     * @throws JwtException
     */
    public Boolean verify(String token) throws JwtException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * 验证 token 是否失效
     *
     * @param token
     * @return true:过期   false:没过期
     */
    public Boolean isExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    /**
     * 根据 userId 和 默认过期时间,生成token,默认签名算法 HS256
     *
     * @param userId 用户 id
     * @param claims 声明内容
     * @return
     */
    public String generate(String userId, Map<String, Object> claims) {
        final Date expirationDate = new Date(System.currentTimeMillis() + defaultExpiredDate * 1000L);
        return generate(userId, expirationDate, claims);
    }

    /**
     * 根据 userId 和 过期时间,生成token,默认签名算法 HS256
     *
     * @param userId      用户 id
     * @param expiredDate token 过期时间
     * @param claims      声明内容
     * @return
     */
    public String generate(String userId, Date expiredDate, Map<String, Object> claims) {
        final Date createdDate = new Date();
        if (claims == null) {
            return Jwts.builder()
                    .setSubject(userId)
                    .setIssuedAt(createdDate)
                    .setExpiration(expiredDate)
                    .signWith(SignatureAlgorithm.HS256, this.jwtSecret)
                    .compact();
        } else {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userId)
                    .setIssuedAt(createdDate)
                    .setExpiration(expiredDate)
                    .signWith(SignatureAlgorithm.HS256, this.jwtSecret)
                    .compact();
        }
    }
}