package com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.security.JwtUtils;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.AuthService;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Vérifier si l'email existe déjà, etc.
        if (userService.findByEmail(request.email) != null) {
            return ResponseEntity.badRequest()
                    .body("L'email existe déjà : " + request.email);
        }

        User user = userService.saveUser(request.email, request.password, request.username, request.role);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.findByEmail(request.email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Utilisateur introuvable pour l'email : " + request.email);
        }
        if (!user.getPassword().equals(request.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Mot de passe invalide");
        }

        String token = JwtUtils.generateToken(user.getEmail());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    static class RegisterRequest {
        public String email;
        public String password;
        public String username;
        public String role;
    }

    static class LoginRequest {
        public String email;
        public String password;
    }

    static class LoginResponse {
        public String token;
        public LoginResponse(String token) {
            this.token = token;
        }
    }
}