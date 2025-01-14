package com.ynov.m2.advanced_software_development.cautious_guacamole.server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.Booking;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository.BookingRepository;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking existingBooking;
    private Booking newBooking;
    private Room room;
    private Date startTime;
    private Date endTime;

    @BeforeEach
    void setUp() {
        // Prépare des objets de test
        room = new Room();
        room.setId(1L);
        room.setName("Salle A");
        
        startTime = new Date(System.currentTimeMillis() + 3600_000);     //  +1h
        endTime   = new Date(System.currentTimeMillis() + 2 * 3600_000); // +2h

        existingBooking = new Booking();
        existingBooking.setId(10L);
        existingBooking.setRoom(room);
        existingBooking.setStartTime(new Date(System.currentTimeMillis() + 30 * 60_000)); // +30 min
        existingBooking.setEndTime(new Date(System.currentTimeMillis() + 90 * 60_000));   // +1h30

        newBooking = new Booking();
        newBooking.setRoom(room);
        newBooking.setStartTime(startTime);
        newBooking.setEndTime(endTime);
    }

    // ------------------------------------------------------------
    // createBooking
    // ------------------------------------------------------------
    @Test
    void testCreateBooking_RoomAvailable() {
        // Arrange
        when(bookingRepository.findByRoomId(room.getId())).thenReturn(Collections.emptyList());
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            b.setId(99L);
            return b;
        });

        // Act
        Booking result = bookingService.createBooking(newBooking);

        // Assert
        assertNotNull(result);
        assertEquals(99L, result.getId());
        verify(bookingRepository, times(1)).findByRoomId(room.getId());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testCreateBooking_RoomNotAvailable() {
        // Arrange
        // Simule l'existence d'une réservation qui se chevauche (existingBooking)
        when(bookingRepository.findByRoomId(room.getId()))
                .thenReturn(List.of(existingBooking));

        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
                bookingService.createBooking(newBooking),
                "Doit lever une exception si la salle n'est pas dispo"
        );

        // On ne doit pas appeler save
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    // ------------------------------------------------------------
    // isRoomAvailable
    // ------------------------------------------------------------
    @Test
    void testIsRoomAvailable_RoomReallyAvailable() {
        // Aucune réservation existante
        when(bookingRepository.findByRoomId(room.getId()))
                .thenReturn(Collections.emptyList());

        boolean available = bookingService.isRoomAvailable(room.getId(), startTime, endTime);
        assertTrue(available);
    }

    @Test
    void testIsRoomAvailable_RoomNotAvailable() {
        // existingBooking chevauche
        when(bookingRepository.findByRoomId(room.getId()))
                .thenReturn(List.of(existingBooking));

        boolean available = bookingService.isRoomAvailable(room.getId(), startTime, endTime);
        assertFalse(available);
    }

    // ------------------------------------------------------------
    // updateReservation
    // ------------------------------------------------------------
    @Test
    void testUpdateReservation_Found() {
        // Arrange
        when(bookingRepository.findById(10L)).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            return b;
        });

        Booking updatedBooking = new Booking();
        updatedBooking.setRoom(room);
        Date newStart = new Date(System.currentTimeMillis() + 2 * 3600_000);
        Date newEnd   = new Date(System.currentTimeMillis() + 3 * 3600_000);
        updatedBooking.setStartTime(newStart);
        updatedBooking.setEndTime(newEnd);

        // Act
        Booking result = bookingService.updateReservation(10L, updatedBooking);

        // Assert
        assertNotNull(result);
        assertEquals(newStart, result.getStartTime());
        assertEquals(newEnd, result.getEndTime());
        assertEquals(room, result.getRoom());
        verify(bookingRepository, times(1)).findById(10L);
        verify(bookingRepository, times(1)).save(existingBooking);
    }

    @Test
    void testUpdateReservation_NotFound() {
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());
        Booking updated = new Booking();

        assertThrows(IllegalArgumentException.class, () ->
                bookingService.updateReservation(999L, updated),
                "Doit lever une exception si la réservation n'existe pas"
        );
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    // ------------------------------------------------------------
    // cancelReservation
    // ------------------------------------------------------------
    @Test
    void testCancelReservation() {
        // Act
        bookingService.cancelReservation(123L);

        // Assert
        verify(bookingRepository, times(1)).deleteById(123L);
    }

    // ------------------------------------------------------------
    // getReservationsByUser / getReservationsBySalle
    // ------------------------------------------------------------
    @Test
    void testGetReservationsByUser() {
        List<Booking> mockBookings = new ArrayList<>();
        mockBookings.add(existingBooking);

        when(bookingRepository.findByUserId(50L)).thenReturn(mockBookings);

        List<Booking> result = bookingService.getReservationsByUser(50L);
        assertEquals(1, result.size());
        assertEquals(existingBooking, result.get(0));

        verify(bookingRepository, times(1)).findByUserId(50L);
    }

    @Test
    void testGetReservationsBySalle() {
        List<Booking> mockBookings = Arrays.asList(existingBooking, newBooking);
        when(bookingRepository.findByRoomId(room.getId())).thenReturn(mockBookings);

        List<Booking> result = bookingService.getReservationsBySalle(room.getId());
        assertEquals(2, result.size());
        assertTrue(result.contains(existingBooking));
        assertTrue(result.contains(newBooking));

        verify(bookingRepository, times(1)).findByRoomId(room.getId());
    }
}
