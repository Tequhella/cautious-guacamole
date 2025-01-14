package com.ynov.m2.advanced_software_development.cautious_guacamole.server.service;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() throws Exception {
        userService = new UserService();
        
        // Injecter manuellement les valeurs pour secretKey et jwtExpirationInMs
        setField(userService, "secretKey", "mySecretKey123");       // Ex. secret
        setField(userService, "jwtExpirationInMs", 3600000L);       // 1h = 3600000 ms
    }

    @Test
    void testGenerateToken() {
        // Préparation
        User testUser = new User();
        testUser.setId(42L);
        testUser.setName("Alice");

        // Action
        String token = callGenerateToken(testUser);

        // Vérification
        assertNotNull(token, "Le token ne doit pas être null");
        assertFalse(token.isEmpty(), "Le token ne doit pas être vide");

        // BONUS : On peut vérifier rapidement qu'il contient un header JWT, ex. "eyJhbGciOiJIUzI1NiIs..."
        // Ce n'est pas un parsing complet, juste un check format.
        assertTrue(token.startsWith("eyJ"), "Le token doit commencer par 'eyJ' (signature JWT)");
    }

    @Test
    void testPasswordMatches_Success() {
        // Dans le code actuel, passwordMatches() fait un simple 'equals()'
        String raw = "mySecret";
        String hashed = "mySecret"; // simulons un "hash" identique
        boolean result = callPasswordMatches(raw, hashed);
        assertTrue(result, "Si raw et hashed sont identiques, passwordMatches doit renvoyer true");
    }

    @Test
    void testPasswordMatches_Failure() {
        String raw = "mySecret";
        String hashed = "otherPassword";
        boolean result = callPasswordMatches(raw, hashed);
        assertFalse(result, "Les mots de passe sont différents, on doit obtenir false");
    }

    /*
     * Exemple si vous décommentez la méthode authenticate(String username, String password) dans UserService :
     *
     *  @Mock
     *  private UserRepository userRepo;
     *
     *  @InjectMocks
     *  private UserService userService;
     *
     *  @Test
     *  void testAuthenticate_Success() {
     *      // Arrange
     *      User user = new User();
     *      user.setId(1L);
     *      user.setName("alice");
     *      user.setPassword("hashedPwd"); // ou la simili-hash
     *
     *      when(userRepo.findByUsername("alice")).thenReturn(Optional.of(user));
     *      doReturn(true).when(userService).passwordMatches("pwd123", "hashedPwd");
     *
     *      // Act
     *      String token = userService.authenticate("alice", "pwd123");
     *
     *      // Assert
     *      assertNotNull(token, "On doit obtenir un token si l'auth est correcte");
     *      verify(userRepo, times(1)).findByUsername("alice");
     *  }
     *
     *  @Test
     *  void testAuthenticate_UserNotFound() {
     *      when(userRepo.findByUsername("inconnu")).thenReturn(Optional.empty());
     *      assertThrows(RuntimeException.class, () -> userService.authenticate("inconnu", "somePwd"));
     *  }
     *
     *  @Test
     *  void testAuthenticate_InvalidPassword() {
     *      User user = new User();
     *      user.setName("bob");
     *      user.setPassword("secret");
     *      when(userRepo.findByUsername("bob")).thenReturn(Optional.of(user));
     *      // passwordMatches sera faux
     *
     *      assertThrows(RuntimeException.class, () -> userService.authenticate("bob", "wrong"));
     *  }
     */

    // ----------------------------------------------------------------
    // Méthodes utilitaires privées pour appeler generateToken/passwordMatches
    // si on ne veut pas changer la visibilité dans UserService
    // ----------------------------------------------------------------
    private String callGenerateToken(User user) {
        try {
            // On peut appeler la méthode private via la réflexion
            var method = UserService.class.getDeclaredMethod("generateToken", User.class);
            method.setAccessible(true);
            return (String) method.invoke(userService, user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean callPasswordMatches(String raw, String hashed) {
        try {
            var method = UserService.class.getDeclaredMethod("passwordMatches", String.class, String.class);
            method.setAccessible(true);
            return (boolean) method.invoke(userService, raw, hashed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
