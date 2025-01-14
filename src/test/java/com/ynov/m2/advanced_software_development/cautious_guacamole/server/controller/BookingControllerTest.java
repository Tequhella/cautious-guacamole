package com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.Booking;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.GuestState;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.State;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.BookingService;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.NotificationService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;


public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private BookingController bookingController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Claims claims;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBooking() {
        Booking booking = new Booking();
        User user = new User();
        GuestState guestState = new GuestState();
        guestState.setUser(user);
        guestState.setState(State.PENDING);
        booking.getGuests().add(guestState);

        Booking savedBooking = new Booking();
        savedBooking.getGuests().add(guestState);

        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("user", User.class)).thenReturn(user);
        when(bookingService.createBooking(booking)).thenReturn(savedBooking);

        ResponseEntity<?> response = bookingController.createBooking(booking, request);
        assertEquals(ResponseEntity.ok(savedBooking), response);
        verify(bookingService, times(1)).createBooking(booking);
        verify(notificationService, times(1)).envoyerNotification(
                anyString(), anyString(), anyString());
    }

    @Test
    public void testCreateBookingWithOwner() {
        Booking booking = new Booking();
        User user = new User();
        GuestState guestState = new GuestState();
        guestState.setUser(user);
        guestState.setState(State.PENDING);
        booking.getGuests().add(guestState);

        User owner = new User();
        GuestState ownerState = new GuestState();
        ownerState.setUser(owner);
        ownerState.setState(State.OWNER);
        Booking savedBooking = new Booking();
        savedBooking.getGuests().add(ownerState);

        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("user", User.class)).thenReturn(user);
        when(bookingService.createBooking(booking)).thenReturn(savedBooking);

        ResponseEntity<?> response = bookingController.createBooking(booking, request);
        assertEquals(ResponseEntity.ok(savedBooking), response);
        verify(bookingService, times(1)).createBooking(booking);
        verify(notificationService, times(1)).envoyerNotification(
                anyString(), anyString(), anyString());
    }
}