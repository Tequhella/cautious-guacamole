package com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Possibilité de méthodes personnalisées, par ex:
    // List<Reservation> findBySalleId(Long salleId);
    // List<Reservation> findByUtilisateurId(Long utilisateurId);
}
