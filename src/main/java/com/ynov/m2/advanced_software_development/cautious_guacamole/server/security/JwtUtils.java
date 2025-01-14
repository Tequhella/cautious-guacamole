package com.ynov.m2.advanced_software_development.cautious_guacamole.server.security;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtils {

    // Clé secrète pour signer les JWT (générée à chaque démarrage)
    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Durée de validité du token (exemple : 1 heure)
    private static final long expirationMs = 3600000;

    /**
     * Génère un token JWT pour le "subject" (souvent l'email).
     */
    public static String generateToken(String subject) {
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + expirationMs);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(expiration)
                .signWith(secretKey)  // signature HMAC avec la clé secrète
                .compact();
    }

    /**
     * Valide et parse un token JWT.
     * - Soulève une exception (JwtException) si invalide ou expiré.
     */
    public Claims parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}