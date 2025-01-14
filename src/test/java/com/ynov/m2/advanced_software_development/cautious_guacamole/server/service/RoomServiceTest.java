package com.ynov.m2.advanced_software_development.cautious_guacamole.server.service;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    private Room roomA;
    private Room roomB;

    @BeforeEach
    void setUp() {
        roomA = new Room();
        roomA.setId(1L);
        roomA.setName("Room A");
        roomA.setSeats(10);
        roomA.setDescription("Petite salle");
        roomA.setEquipments("Projector");

        roomB = new Room();
        roomB.setId(2L);
        roomB.setName("Room B");
        roomB.setSeats(20);
        roomB.setDescription("Grande salle");
        roomB.setEquipments("TV, Whiteboard");
    }

    @Test
    void testGetAllRooms() {
        // Arrange
        List<Room> mockRooms = Arrays.asList(roomA, roomB);
        when(roomRepository.findAll()).thenReturn(mockRooms);

        // Act
        List<Room> result = roomService.getAllRooms();

        // Assert
        assertNotNull(result, "La liste des salles ne doit pas être nulle");
        assertEquals(2, result.size(), "On doit trouver deux salles");
        assertSame(roomA, result.get(0));
        assertSame(roomB, result.get(1));
        verify(roomRepository, times(1)).findAll();
    }

    /*
    // Exemple si vous réactivez la méthode createRoom(...)

    @Test
    void testCreateRoom_Success() {
        // Arrange
        Room newRoom = new Room();
        newRoom.setName("Room C");
        newRoom.setSeats(30);
        newRoom.setDescription("Salle de conférence");
        newRoom.setEquipments("Microphone, Projector");

        when(roomRepository.findRoomByName("Room C")).thenReturn(null);
        when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> {
            Room r = invocation.getArgument(0);
            r.setId(99L); // Simule la génération d'ID par la DB
            return r;
        });

        // Act
        Room created = roomService.createRoom(newRoom);

        // Assert
        assertNotNull(created);
        assertEquals(99L, created.getId());
        assertEquals("Room C", created.getName());
        verify(roomRepository, times(1)).findRoomByName("Room C");
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testCreateRoom_NameIsBlank() {
        // Arrange
        Room newRoom = new Room();
        newRoom.setName("   "); // Blank
        // seats, description, etc.

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(newRoom),
                "Le nom de la salle est obligatoire");
        verify(roomRepository, never()).findRoomByName(anyString());
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testCreateRoom_DuplicateName() {
        // Arrange
        Room newRoom = new Room();
        newRoom.setName("Room A"); // Room A existe déjà
        when(roomRepository.findRoomByName("Room A")).thenReturn(roomA);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> roomService.createRoom(newRoom),
                "Une salle avec ce nom existe déjà !");
        verify(roomRepository, times(1)).findRoomByName("Room A");
        verify(roomRepository, never()).save(any(Room.class));
    }
    */
}
