package com.careerclub.careerclub.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JwtGenerateToken {

    public static String generateAccessToken(String secret, String username, Date expirationTime) {
        return generateAccessToken(secret, username, new HashMap<>(), expirationTime);
    }

    public static String generateAccessToken(String secret, String username, HashMap<String, Object> claims, Date expirationTime) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        var accessToken = Jwts.builder().setSubject(username)
                .addClaims(claims)
                .setExpiration(expirationTime).signWith(key).compact();
        return accessToken;
    }
}
