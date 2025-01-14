package com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller.AuthController.LoginRequest;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller.AuthController.LoginResponse;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.UserTest;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.AuthService;
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
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;
    private UserTest newUser;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        // Settez vos identifiants de test
        // Par exemple:
        // loginRequest.username = "testUser";
        // loginRequest.password = "testPassword";
        
        // Comme les champs de LoginRequest sont package-private,
        // on peut les renseigner via la réflexion ou créer un constructeur.
        // Ici, on utilise l'accès direct si on est dans le même package.
        // Sinon, adaptez selon votre besoin.

        newUser = new UserTest();
        newUser.setUsername("newUser");
        newUser.setPassword("newPass");
        newUser.setEmail("newuser@example.com");
    }

    @Test
    void testLogin_Success() throws Exception {
        // Arrange
        // Simule le comportement : si on appelle authService.authenticate avec user= "testUser" / pass= "testPassword",
        // on retourne un token fictif.
        when(authService.authenticate(anyString(), anyString()))
                .thenReturn("fake-jwt-token");

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof LoginResponse);

        LoginResponse loginResponse = (LoginResponse) response.getBody();
        assertEquals("fake-jwt-token", loginResponse.getToken());
        verify(authService, times(1))
                .authenticate(loginRequest.username, loginRequest.password);
    }

    @Test
    void testLogin_Failure() throws Exception {
        // Arrange
        // Simule un échec d'authentification -> on lève une exception
        when(authService.authenticate(anyString(), anyString()))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
        verify(authService, times(1))
                .authenticate(loginRequest.username, loginRequest.password);
    }

    @Test
    void testRegister_Success() throws Exception {
        // Arrange
        // Simule l'inscription réussie de l'utilisateur
        UserTest createdUser = new UserTest();
        createdUser.setId(1L);
        createdUser.setUsername(newUser.getUsername());
        createdUser.setPassword(newUser.getPassword());
        createdUser.setEmail(newUser.getEmail());

        when(authService.register(any(UserTest.class))).thenReturn(createdUser);

        // Act
        ResponseEntity<?> response = authController.register(newUser);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // On s'attend à recevoir l'utilisateur créé en body
        assertTrue(response.getBody() instanceof UserTest);
        UserTest responseUser = (UserTest) response.getBody();
        assertEquals(1L, responseUser.getId());
        assertEquals("newUser", responseUser.getUsername());
        assertEquals("newuser@example.com", responseUser.getEmail());

        // Vérifie qu'on a bien appelé authService.register une seule fois
        verify(authService, times(1)).register(any(UserTest.class));
    }

    @Test
    void testRegister_Failure() throws Exception {
        // Arrange
        // Simule un échec d'inscription -> on lève une exception
        when(authService.register(any(UserTest.class)))
                .thenThrow(new RuntimeException("User already exists"));

        // Act
        ResponseEntity<?> response = authController.register(newUser);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
        verify(authService, times(1)).register(any(UserTest.class));
    }
}