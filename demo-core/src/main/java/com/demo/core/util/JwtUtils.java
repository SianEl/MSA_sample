package com.demo.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

public class JwtUtils {
    private static final byte[] SESSION_KEY = "youcannotopenthiswithoutbo11stsecretkey".getBytes();
    private static final String ADMIN_ID_KEY = "adminId";
    private static final String SESSION_ID_KEY = "sessionId";

    public static String createSessionToken(String userId, String sessionId) {
        if (!(StringUtils.hasText(userId) && StringUtils.hasText(sessionId))) {
            return null;
        }
        String token = Jwts.builder()
                .claim(ADMIN_ID_KEY, userId)
                .claim(SESSION_ID_KEY, sessionId)
                .signWith(SignatureAlgorithm.HS256, SESSION_KEY)
                .compact();

        return token;
    }

    public static String getUserId(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SESSION_KEY).parseClaimsJws(token).getBody();
            return (String) claims.get(ADMIN_ID_KEY);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSessionId(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SESSION_KEY).parseClaimsJws(token).getBody();
            return (String) claims.get(SESSION_ID_KEY);
        } catch (Exception e) {
            return null;
        }
    }
}
