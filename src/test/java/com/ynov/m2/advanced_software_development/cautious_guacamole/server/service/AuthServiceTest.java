package com.ynov.m2.advanced_software_development.cautious_guacamole.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private User existingUser;

    @BeforeEach
    void setUp() {
        // Création d'un utilisateur déjà existant en BDD, avec un mot de passe haché
        existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("alice"); // correspond au "username"
        existingUser.setEmail("alice@example.com");

        // Hachage en dur pour le test (cela correspond à "pwd123")
        String hashedPassword = BCrypt.hashpw("pwd123", BCrypt.gensalt());
        existingUser.setPassword(hashedPassword);
        existingUser.setRole("USER");
    }

    @Test
    void testAuthenticate_Success() {
        // Arrange
        when(userRepository.findByUsername("alice")).thenReturn(existingUser);

        // Act
        // Le mot de passe en clair correspond à "pwd123" haché précédemment
        String token = authService.authenticate("alice", "pwd123");

        // Assert
        assertNotNull(token, "Avec un utilisateur existant et un mot de passe valide, on doit obtenir un token");
        // On peut vérifier la longueur du token, son format, etc.
        verify(userRepository, times(1)).findByUsername("alice");
    }

    @Test
    void testAuthenticate_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("inconnu")).thenReturn(null);

        // Act
        String token = authService.authenticate("inconnu", "anyPassword");

        // Assert
        assertNull(token, "Si l'utilisateur n'existe pas, on doit renvoyer null");
        verify(userRepository, times(1)).findByUsername("inconnu");
    }

    @Test
    void testAuthenticate_InvalidPassword() {
        // Arrange
        when(userRepository.findByUsername("alice")).thenReturn(existingUser);

        // Act
        // On utilise un mot de passe incorrect
        String token = authService.authenticate("alice", "wrong_password");

        // Assert
        assertNull(token, "Si le mot de passe est faux, on doit renvoyer null");
        verify(userRepository, times(1)).findByUsername("alice");
    }

    @Test
    void testRegister_Success() {
        // Arrange
        // On simule que l'utilisateur "bob" n'existe pas
        when(userRepository.findByUsername("bob")).thenReturn(null);

        User newUser = new User();
        newUser.setName("bob"); // newUser.getName() est "bob"
        newUser.setPassword("pwdBob");
        newUser.setEmail("bob@example.com");

        // On simule la sauvegarde qui renvoie un utilisateur avec un ID
        User savedUser = new User();
        savedUser.setId(2L);
        savedUser.setName("bob");
        savedUser.setPassword("hashedPwd");
        savedUser.setEmail("bob@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = authService.register(newUser);

        // Assert
        assertNotNull(result, "Le nouvel utilisateur doit être créé");
        assertEquals(2L, result.getId(), "L'ID doit être défini après sauvegarde");
        assertEquals("bob", result.getName());
        // On ne peut pas trop vérifier le password, car il est haché
        // On s'assure simplement qu'il n'est pas vide
        assertNotNull(result.getPassword());

        verify(userRepository, times(1)).findByUsername("bob");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_UserAlreadyExists() {
        // Arrange
        when(userRepository.findByUsername("alice")).thenReturn(existingUser);

        User newUser = new User();
        newUser.setName("alice"); // "alice" existe déjà
        newUser.setPassword("pwd");
        newUser.setEmail("alice@again.com");

        // Act
        User result = authService.register(newUser);

        // Assert
        assertNull(result, "Si l'utilisateur existe déjà, on renvoie null");
        verify(userRepository, times(1)).findByUsername("alice");
        // On ne doit pas appeler save
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Si vous souhaitez tester (et moquer) la génération du token JwtUtils.generateToken(...),
     * vous pouvez utiliser un MockedStatic :
     *
     * try (MockedStatic<JwtUtils> utilities = Mockito.mockStatic(JwtUtils.class)) {
     *     utilities.when(() -> JwtUtils.generateToken(any(User.class)))
     *              .thenReturn("fake-jwt");
     *     // ... exécuter le test ...
     * }
     */
}
