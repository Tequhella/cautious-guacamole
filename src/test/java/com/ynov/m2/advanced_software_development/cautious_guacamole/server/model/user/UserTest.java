package com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User emptyUser;
    private User paramUser;

    @BeforeEach
    void setUp() {
        // Constructeur vide
        emptyUser = new User();

        // Constructeur paramétré
        paramUser = new User("JohnDoe", "johndoe@example.com", "ADMIN");
        // Remarque: le champ 'password' n'est pas renseigné par ce constructeur. 
        // Dans l'implémentation réelle, il faudra probablement le setter séparément.
    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(emptyUser);
        assertNull(emptyUser.getId());
        assertNull(emptyUser.getName());
        assertNull(emptyUser.getEmail());
        assertNull(emptyUser.getPassword());
        assertNull(emptyUser.getRole());
    }

    @Test
    void testParameterizedConstructor() {
        assertNotNull(paramUser);
        assertNull(paramUser.getId(), 
            "L'ID devrait être nul avant sauvegarde en base (auto-généré).");
        assertEquals("JohnDoe", paramUser.getName());
        assertEquals("johndoe@example.com", paramUser.getEmail());
        assertEquals("ADMIN", paramUser.getRole());
        assertNull(paramUser.getPassword(), 
            "Le constructeur paramétré ne définit pas le mot de passe.");
    }

    @Test
    void testSettersAndGetters() {
        // On modifie l'utilisateur vide pour vérifier les getters/setters
        emptyUser.setId(123L);
        emptyUser.setName("Alice");
        emptyUser.setEmail("alice@example.com");
        emptyUser.setPassword("pwd123");
        emptyUser.setRole("USER");

        assertEquals(123L, emptyUser.getId());
        assertEquals("Alice", emptyUser.getName());
        assertEquals("alice@example.com", emptyUser.getEmail());
        assertEquals("pwd123", emptyUser.getPassword());
        assertEquals("USER", emptyUser.getRole());
    }
}