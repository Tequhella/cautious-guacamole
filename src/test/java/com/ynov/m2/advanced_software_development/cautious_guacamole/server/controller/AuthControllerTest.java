package com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller.AuthController.LoginRequest;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller.AuthController.RegisterRequest;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.security.JwtUtils;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private JwtUtils jwtUtils; // Vous pouvez injecter JwtUtils en bean, ou moquer ses méthodes statiques
    
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    private User mockUser;

    @BeforeEach
    void setUp() {
        // Préparation de données factices
        registerRequest = new RegisterRequest();
        registerRequest.email = "alice@example.com";
        registerRequest.password = "secret";
        registerRequest.username = "Alice";
        registerRequest.role = "USER";

        loginRequest = new LoginRequest();
        loginRequest.email = "alice@example.com";
        loginRequest.password = "secret";

        mockUser = new User();
        mockUser.setEmail("alice@example.com");
        mockUser.setPassword("secret");
        mockUser.setName("Alice");
        mockUser.setRole("USER");
    }

    // -------------------------------------------------------------------
    // Tests pour REGISTER
    // -------------------------------------------------------------------
    @Test
    void testRegister_EmailAlreadyExists() {
        // Arrange
        // On simule que l'email existe déjà
        when(userService.findByEmail("alice@example.com")).thenReturn(mockUser);

        // Act
        ResponseEntity<?> response = authController.register(registerRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof String);
        assertTrue(response.getBody().toString().contains("L'email existe déjà"));

        verify(userService, times(1)).findByEmail("alice@example.com");
        // Puisqu'on a détecté un doublon, on ne crée pas l'utilisateur
        verify(userService, never()).saveUser(any(), any(), any(), any());
    }

    @Test
    void testRegister_Success() {
        // Arrange
        // On simule que l'email n'existe pas
        when(userService.findByEmail("alice@example.com")).thenReturn(null);

        // On simule la création d'un nouvel user
        User savedUser = new User();
        savedUser.setEmail("alice@example.com");
        savedUser.setPassword("secret");
        savedUser.setName("Alice");
        savedUser.setRole("USER");

        when(userService.saveUser("alice@example.com", "secret", "Alice", "USER"))
                .thenReturn(savedUser);

        // Act
        ResponseEntity<?> response = authController.register(registerRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(savedUser, response.getBody());
        // Vérifie qu'on a bien appelé userService.saveUser
        verify(userService, times(1))
                .saveUser("alice@example.com", "secret", "Alice", "USER");
    }

    // -------------------------------------------------------------------
    // Tests pour LOGIN
    // -------------------------------------------------------------------
    @Test
    void testLogin_UserNotFound() {
        // Arrange
        when(userService.findByEmail("bob@example.com")).thenReturn(null);

        // On modifie le loginRequest pour forcer un email inconnu
        loginRequest.email = "bob@example.com";

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Utilisateur introuvable"));

        verify(userService, times(1)).findByEmail("bob@example.com");
    }

    @Test
    void testLogin_InvalidPassword() {
        // Arrange
        // L'utilisateur existe, mais le mot de passe ne correspond pas
        when(userService.findByEmail("alice@example.com")).thenReturn(mockUser);

        loginRequest.password = "wrongPassword";

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Mot de passe invalide"));

        verify(userService, times(1)).findByEmail("alice@example.com");
    }

    @Test
    void testLogin_Success() {
        // Arrange
        when(userService.findByEmail("alice@example.com")).thenReturn(mockUser);

        // Pour simuler la génération de token,
        // on peut juste appeler la méthode statique 'JwtUtils.generateToken(...)'.
        // Si JwtUtils est un bean, vous pouvez moquer 'jwtUtils.generateToken(...)'.
        try (MockedStatic<JwtUtils> mockedStatic = mockStatic(JwtUtils.class)) {
            mockedStatic.when(() -> JwtUtils.generateToken("alice@example.com"))
                        .thenReturn("fake-jwt-token");

            // Act
            ResponseEntity<?> response = authController.login(loginRequest);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody() instanceof AuthController.LoginResponse);

            AuthController.LoginResponse body = (AuthController.LoginResponse) response.getBody();
            assertEquals("fake-jwt-token", body.token);

            verify(userService, times(1)).findByEmail("alice@example.com");
            mockedStatic.verify(() -> JwtUtils.generateToken("alice@example.com"), times(1));
        }
    }
}