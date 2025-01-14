package com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GuestStateTest {

    private GuestState guestState;

    @BeforeEach
    void setUp() {
        guestState = new GuestState();
    }

    @Test
    void testDefaultValues() {
        assertNull(guestState.getId());
        assertNull(guestState.getUser());
        assertNull(guestState.getBooking());
        assertNull(guestState.getState());
    }

    @Test
    void testIdGetterSetter() {
        guestState.setId(10L);
        assertEquals(10L, guestState.getId());
    }

    @Test
    void testUserGetterSetter() {
        User user = new User();
        user.setId(1L);
        // Remplacez setUsername par une méthode existante pour définir le nom d'utilisateur
        user.setName("testUser");

        guestState.setUser(user);

        assertNotNull(guestState.getUser());
        assertEquals(1L, guestState.getUser().getId());
        assertEquals("testUser", guestState.getUser().getName());
    }

    @Test
    void testBookingGetterSetter() {
        Booking booking = new Booking();
        booking.setId(2L);

        guestState.setBooking(booking);

        assertNotNull(guestState.getBooking());
        assertEquals(2L, guestState.getBooking().getId());
    }

    @Test
    void testStateGetterSetter() {
        guestState.setState(State.PENDING);
        assertEquals(State.PENDING, guestState.getState());

        guestState.setState(State.OWNER);
        assertEquals(State.OWNER, guestState.getState());
    }
}