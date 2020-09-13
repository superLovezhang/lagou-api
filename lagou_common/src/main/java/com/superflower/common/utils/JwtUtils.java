package com.superflower.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.superflower.common.entity.User;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static String secret = "asyduiaa";

    public static String encode(User user) {
        long currentTimeMillis = System.currentTimeMillis();
        Date expire = new Date(currentTimeMillis + (1000 * 60 * 60 * 5));
        return Jwts.builder()
                .setExpiration(expire)
                .claim("username", user.getUsername())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public static String encode(String object) throws JsonProcessingException {

        long currentTimeMillis = System.currentTimeMillis();
        Date expire = new Date(currentTimeMillis + (1000 * 60 * 60 * 5));
        return Jwts.builder()
                .setExpiration(expire)
                .claim("admin", object)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public static boolean checkToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Map decode(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
