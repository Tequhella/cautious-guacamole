package com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;

class BookingTest {

    private Booking emptyBooking;
    private Booking bookingWithParams;
    private Room room;
    private List<GuestState> guests;
    private Date startTime;
    private Date endTime;

    @BeforeEach
    void setUp() {
        // Initialisation des objets communs
        room = new Room();
        room.setId(1L);
        room.setName("Salle 101");

        guests = new ArrayList<>();
        GuestState gs1 = new GuestState();
        gs1.setState(State.PENDING);
        guests.add(gs1);

        startTime = new Date();
        endTime = new Date(startTime.getTime() + 3600_000); // +1 heure

        // Booking vide
        emptyBooking = new Booking();

        // Booking avec paramètres
        bookingWithParams = new Booking(room, guests, startTime, endTime);
    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(emptyBooking);
        assertNull(emptyBooking.getRoom());
        assertNotNull(emptyBooking.getGuests());  // liste vide instanciée par défaut
        assertTrue(emptyBooking.getGuests().isEmpty());
        assertNull(emptyBooking.getStartTime());
        assertNull(emptyBooking.getEndTime());
    }

    @Test
    void testAllArgsConstructor() {
        assertNotNull(bookingWithParams);
        assertEquals(room, bookingWithParams.getRoom());
        assertEquals(1, bookingWithParams.getGuests().size());
        assertEquals(startTime, bookingWithParams.getStartTime());
        assertEquals(endTime, bookingWithParams.getEndTime());
    }

    @Test
    void testSettersAndGetters() {
        // On modifie le booking vide via les setters
        emptyBooking.setId(99L);
        emptyBooking.setRoom(room);
        emptyBooking.setGuests(guests);
        emptyBooking.setStartTime(startTime);
        emptyBooking.setEndTime(endTime);

        // On vérifie via les getters
        assertEquals(99L, emptyBooking.getId());
        assertEquals(room, emptyBooking.getRoom());
        assertEquals(1, emptyBooking.getGuests().size());
        assertEquals(startTime, emptyBooking.getStartTime());
        assertEquals(endTime, emptyBooking.getEndTime());
    }

    @Test
    void testGuestListManipulation() {
        // Vérifier l'ajout direct dans la liste de l'objet booking
        emptyBooking.getGuests().addAll(guests);
        assertEquals(1, emptyBooking.getGuests().size());
        
        // Ajouter un nouveau GuestState
        GuestState gs2 = new GuestState();
        gs2.setState(State.OWNER);
        emptyBooking.getGuests().add(gs2);

        assertEquals(2, emptyBooking.getGuests().size());
        assertSame(gs2, emptyBooking.getGuests().get(1));
    }
}