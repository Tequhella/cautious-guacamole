package com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.Booking;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.GuestState;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.State;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.RoomService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Room room,
                                           HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            // Pas d’utilisateur authentifié → 401
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Room saved = roomService.createRoom(room);

        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        // 1) Récupérer l’authentification courante depuis le SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            // Pas d’utilisateur authentifié → 401
            return ResponseEntity.status(401).body("Unauthorized");
        }

        List<Room> rooms = roomService.getAllRooms();
        if (rooms == null) {
            return ResponseEntity.badRequest().body("Pas de salles trouvées...");
        }

        return ResponseEntity.ok(rooms);
    }

}
