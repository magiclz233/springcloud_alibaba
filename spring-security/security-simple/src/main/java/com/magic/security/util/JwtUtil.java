package com.magic.security.util;

import com.magic.security.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.List;

/**
 * @author magic_lz
 * @version 1.0
 * @date 2020/8/27 23:24
 */
public class JwtUtil {

    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    public static final byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(API_KEY_SECRET_BYTES);


    /**
     * 生成 Token 传入 username role集合 是否记住账号
     *
     * @param username     用户名
     * @param roles        用户权限
     * @param isRememberMe 是否记住我
     * @return token
     */
    public static String creatToken(String username, List<String> roles, boolean isRememberMe) {
        // 是否记住账号  记住: 7天token有效期  不记住: 1小时
        long expireDate = isRememberMe ? SecurityConstants.EXPIRATION_REMEMBER : SecurityConstants.EXPIRATION;
        // 当前时间
        final Date nowDate = new Date();
        // token 过期时间
        final Date expirationDate = new Date(nowDate.getTime() + expireDate * 1000);
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("type",SecurityConstants.TOKEN_TYPE)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .claim(SecurityConstants.ROLE_CLAIMS,String.join(",",roles))
                .setIssuer("Magic")
                .setIssuedAt(nowDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        return SecurityConstants.TOKEN_PREFIX + tokenPrefix;
    }


}
