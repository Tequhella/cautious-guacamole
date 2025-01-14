package com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.Booking;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.GuestState;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.State;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository.BookingRepository;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.BookingService;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.NotificationService;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking,
                                               HttpServletRequest request) {

        GuestState guestState = new GuestState();
        guestState.setUser(booking.getUser());
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
    public ResponseEntity<?> getMyReservations() {
        // 1) Récupérer l’authentification courante depuis le SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            // Pas d’utilisateur authentifié → 401
            return ResponseEntity.status(401).body("Unauthorized");
        }

        // 2) Le principal est le "subject" qu’on a mis dans JwtFilter (souvent un email ou un userId)
        String currentEmail = (String) auth.getPrincipal();
        // Ex. si on a fait: new UsernamePasswordAuthenticationToken(subject, null, authorities);

        // 3) Récupérer l’utilisateur en BDD (optionnel si on n’a besoin que de l’email)
        User user = userService.findByEmail(currentEmail);
        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur introuvable pour l'email : " + currentEmail);
        }

        // 4) Récupérer les bookings de l’utilisateur
        List<Booking> bookings = bookingService.getReservationsByUser(user.getId());

        // 5) Retourner la liste
        return ResponseEntity.ok(bookings);
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
