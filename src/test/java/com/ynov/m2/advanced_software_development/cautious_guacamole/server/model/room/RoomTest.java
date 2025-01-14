package com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomTest {

    private Room emptyRoom;
    private Room paramRoom;

    @BeforeEach
    void setUp() {
        // Constructeur vide
        emptyRoom = new Room();

        // Constructeur avec paramètres
        paramRoom = new Room("Room 101", 10, "Description sample", "Equipments sample");
    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(emptyRoom);
        assertNull(emptyRoom.getId(), "L'id doit être null par défaut");
        assertNull(emptyRoom.getName(), "Le name doit être null par défaut");
        assertEquals(0, emptyRoom.getSeats(), "Le seats doit être 0 par défaut");
        assertNull(emptyRoom.getDescription(), "La description doit être null par défaut");
        assertNull(emptyRoom.getEquipments(), "Les équipements doivent être null par défaut");
    }

    @Test
    void testParameterizedConstructor() {
        // Vérifie que l'objet paramRoom a les valeurs attendues
        assertNotNull(paramRoom);
        // L'id n’est pas passé en paramètre dans le constructeur, donc reste null
        assertNull(paramRoom.getId());
        assertEquals("Room 101", paramRoom.getName());
        assertEquals(10, paramRoom.getSeats());
        assertEquals("Description sample", paramRoom.getDescription());
        assertEquals("Equipments sample", paramRoom.getEquipments());
    }

    @Test
    void testSettersAndGetters() {
        // On modifie le Room vide pour vérifier les getters/setters
        emptyRoom.setId(999L);
        emptyRoom.setName("New Room");
        emptyRoom.setSeats(25);
        emptyRoom.setDescription("A brand new room");
        emptyRoom.setEquipments("Projector, Whiteboard");

        assertEquals(999L, emptyRoom.getId());
        assertEquals("New Room", emptyRoom.getName());
        assertEquals(25, emptyRoom.getSeats());
        assertEquals("A brand new room", emptyRoom.getDescription());
        assertEquals("Projector, Whiteboard", emptyRoom.getEquipments());
    }
}