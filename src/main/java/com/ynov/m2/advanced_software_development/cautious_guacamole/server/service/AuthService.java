package com.ynov.m2.advanced_software_development.cautious_guacamole.server.service;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository.UserRepository;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.security.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder; // Nécessite spring-security-crypto
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        if (BCrypt.checkpw(password, user.getPassword())) {
            return JwtUtils.generateToken(user);
        }
        return null;
    }

    public User register(String username, String password, String email, String role) {
        if (userRepository.findByUsername(username) != null) {
            return null;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User newUser = new User();
        newUser.setName(username);
        newUser.setPassword(hashedPassword);
        newUser.setEmail(email);
        newUser.setRole(role);

        return userRepository.save(newUser);
    }

}

//@Service
//public class AuthService {
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.expiration-in-ms}")
//    private long jwtExpirationInMs;
//
//    @Autowired
//    private UserRepository utilisateurRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    /**
//     * Permet l'inscription d'un nouvel utilisateur (optionnel).
//     * @param user l'utilisateur à créer
//     * @return l'objet Utilisateur créé et sauvegardé
//     * @throws IllegalStateException si le username est déjà pris
//     */
//    public User register(User user) {
//        // Vérifier si le username existe déjà
//        Optional<User> existing = utilisateurRepository.findByUsername(user.getName());
//        if (existing.isPresent()) {
//            throw new IllegalStateException("Le nom d'utilisateur est déjà pris !");
//        }
//
//        // Hash du mot de passe
//        String hashedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(hashedPassword);
//
//        // Enregistrer l'utilisateur
//        return utilisateurRepository.save(user);
//    }
//
//    /**
//     * Permet de vérifier l'authentification d'un utilisateur (login).
//     * @param username identifiant
//     * @param rawPassword mot de passe en clair (fourni par l'utilisateur)
//     * @return un token JWT valide si l'authentification réussit
//     * @throws RuntimeException si les identifiants sont invalides
//     */
//    public String authenticate(String username, String rawPassword) {
//        // Rechercher l’utilisateur
//        User user = utilisateurRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable : " + username));
//
//        // Vérifier le mot de passe (comparaison avec le hash stocké)
//        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
//            throw new RuntimeException("Mot de passe invalide !");
//        }
//
//        // Générer le token JWT
//        return generateToken(user);
//    }
//
//    /**
//     * Génère un JWT pour un utilisateur donné.
//     * @param user objet Utilisateur
//     * @return le token JWT sous forme de String
//     */
//    private String generateToken(User user) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs); // expiration dans X ms
//
//        // Le "subject" peut être le username ou l'ID
//        return Jwts.builder()
//                .setSubject(user.getName())
//                .claim("role", user.isAdmin())     // si vous voulez embarquer le rôle
//                .claim("userId", user.getId())    // embarquer l'ID si besoin
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // signature
//                .compact();
//    }
//
//    /**
//     * (Optionnel) Méthode pour valider un token JWT et retourner les claims.
//     * Peut servir dans un JwtFilter si vous n'utilisez pas déjà Spring Security complet.
//     */
//    public Claims validateToken(String token) {
//        try {
//            return Jwts.parser()
//                    .setSigningKey(secretKey.getBytes())
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (JwtException | IllegalArgumentException e) {
//            // Token invalide ou expiré
//            throw new RuntimeException("Token invalide ou expiré", e);
//        }
//    }
//}