package com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EntityManager entityManager;

    private Room roomA;
    private Room roomB;

    @BeforeEach
    void setUp() {
        // Création de deux Room
        roomA = new Room();
        roomA.setName("Room A");
        roomA.setSeats(10);
        roomA.setDescription("Petite salle");
        roomA.setEquipments("Projector, Whiteboard");

        roomB = new Room();
        roomB.setName("Room B");
        roomB.setSeats(20);
        roomB.setDescription("Grande salle");
        roomB.setEquipments("TV, Microphone");

        // Persistance pour générer les IDs
        entityManager.persist(roomA);
        entityManager.persist(roomB);
        // Flush pour forcer l’insertion en base avant les tests
        entityManager.flush();
    }
}
