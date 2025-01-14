package com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    private User userAlice;
    private User userBob;

    @BeforeEach
    void setUp() {
        // Création de deux utilisateurs
        userAlice = new User();
        userAlice.setName("alice");       // correspond à l'annotation @Column(unique=true, ...)
        userAlice.setEmail("alice@example.com");
        userAlice.setPassword("passwordAlice");
        userAlice.setRole("USER");

        userBob = new User();
        userBob.setName("bob");
        userBob.setEmail("bob@example.com");
        userBob.setPassword("passwordBob");
        userBob.setRole("ADMIN");

        // Persister ces deux utilisateurs
        entityManager.persist(userAlice);
        entityManager.persist(userBob);
        // Flush pour s’assurer que les opérations d’insertion sont exécutées
        entityManager.flush();
    }

    @Test
    @DisplayName("findById(...) - Doit retrouver le User correspondant à l'ID")
    void testFindById() {
        // Récupération de l'utilisateur 'userAlice' par son ID
        Optional<User> optionalUser = userRepository.findById(userAlice.getId());
        assertTrue(optionalUser.isPresent(), "UserAlice doit être trouvé par son ID");
        assertEquals(userAlice.getName(), optionalUser.get().getName());
        assertEquals(userAlice.getEmail(), optionalUser.get().getEmail());
    }

    @Test
    @DisplayName("findByUsername(...) - Doit retrouver l'utilisateur par son username")
    void testFindByUsername() {
        // Supposons que "name" est le champ unique faisant office de username
        User found = userRepository.findByUsername("alice");
        assertNotNull(found, "L'utilisateur 'alice' doit être trouvé");
        assertEquals("alice", found.getName());
        assertEquals("alice@example.com", found.getEmail());
    }

    @Test
    @DisplayName("findByUsername(...) - Doit retourner null si l'utilisateur n'existe pas")
    void testFindByUsernameNotFound() {
        User found = userRepository.findByUsername("inexistant");
        assertNull(found, "Aucun utilisateur ne doit être trouvé pour 'inexistant'");
    }
}
