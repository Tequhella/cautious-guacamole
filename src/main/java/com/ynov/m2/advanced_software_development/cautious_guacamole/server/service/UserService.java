package com.ynov.m2.advanced_software_development.cautious_guacamole.server.service;

import java.sql.Date;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration-in-ms}")
    private long jwtExpirationInMs;

    @Autowired
    private UserRepository userRepository;

    //public String authenticate(String username, String password) {
    //    User user = userRepo.findByUsername(username)
    //            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
//
    //    // Vérifier le password (hash bcrypt par ex.)
    //    if (!passwordMatches(password, user.getPassword())) {
    //        throw new RuntimeException("Mot de passe invalide");
    //    }
//
    //    return generateToken(user);
    //}

    private String generateToken(User user) {
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(user.getName())
                .claim("userId", user.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        // TODO: vérifier avec un encodeur bcrypt/BCryptPasswordEncoder
        return rawPassword.equals(hashedPassword);
    }

    public User saveUser(String email, String password, String usernme, String role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(usernme);
        user.setRole(role);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}