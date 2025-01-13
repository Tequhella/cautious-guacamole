package com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.Booking;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.GuestState;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.State;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository.BookingRepository;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.BookingService;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.NotificationService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking,
                                               HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        User user = claims.get("user", User.class);

        GuestState guestState = new GuestState();
        guestState.setUser(user);
        guestState.setState(State.PENDING);
        booking.getGuests().add(guestState);

        Booking saved = bookingService.createBooking(booking);

        User owner = null;

        for (GuestState gs : saved.getGuests()) {
            if (gs.getState() == State.OWNER) {
                owner = gs.getUser();
                break;
            }
        }

        assert owner != null;
        notificationService.envoyerNotification(
                owner.getEmail(),
                "Réservation confirmée",
                "Votre réservation a bien été créée pour la salle " + saved.getRoom().getName()
        );

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/me")
    public ResponseEntity<List<Booking>> getMyReservations(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long userId = claims.get("userId", Long.class);
        List<Booking> reservations = bookingService.getReservationsByUser(userId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable("id") Long reservationId) {
        bookingService.cancelReservation(reservationId);
        return ResponseEntity.ok("Réservation annulée !");
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Booking>> getReservationsBySalle(@PathVariable("salleId") Long salleId) {
        List<Booking> reservations = bookingService.getReservationsBySalle(salleId);
        return ResponseEntity.ok(reservations);
    }

}
