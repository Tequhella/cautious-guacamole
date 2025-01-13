package com.ynov.m2.advanced_software_development.cautious_guacamole.server.security;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    private static final String SECRET_KEY = "ChangeMeInProduction";
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}