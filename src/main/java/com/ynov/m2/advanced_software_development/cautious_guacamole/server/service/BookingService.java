package com.ynov.m2.advanced_software_development.cautious_guacamole.server.service;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.Booking;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository.BookingRepository;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    private final Lock bookingLock = new ReentrantLock();

    public Booking createBooking(Booking booking) {
        bookingLock.lock();
        try {
            if (!isRoomAvailable(booking.getRoom().getId(), booking.getStartTime(), booking.getEndTime())) {
                throw new IllegalStateException("La salle est déjà réservée pour ce créneau !");
            }
            return bookingRepository.save(booking);
        } finally {
            bookingLock.unlock();
        }
    }

    public boolean isRoomAvailable(Long roomId, Date start, Date end) {
        List<Booking> existingBookings = bookingRepository.findByRoomId(roomId);
        for (Booking booking : existingBookings) {
            if (booking.getStartTime().before(end) && booking.getEndTime().after(start)) {
                return false;
            }
        }
        return true;
    }

    public Booking updateReservation(Long bookingId, Booking updated) {
        Booking existing = bookingRepository.findById(bookingId).orElse(null);
        if (existing == null) {
            throw new IllegalArgumentException("Réservation introuvable");
        }
        existing.setStartTime(updated.getStartTime());
        existing.setEndTime(updated.getEndTime());
        existing.setRoom(updated.getRoom());
        return bookingRepository.save(existing);
    }

    public void cancelReservation(Long reservationId) {
        bookingRepository.deleteById(reservationId);
    }

    public List<Booking> getReservationsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getReservationsBySalle(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

}

//@Service
//public class BookingService {
//
//    private final BookingRepository bookingRepository;
//    private final RoomRepository roomRepository;
//
//    private final ReentrantLock bookingLock = new ReentrantLock();
//
//    @Autowired
//    public BookingService(BookingRepository bookingRepository,
//                              RoomRepository roomRepository) {
//        this.bookingRepository = bookingRepository;
//        this.roomRepository = roomRepository;
//    }
//
//    /**
//     * Récupère toutes les réservations
//     */
//    public List<Booking> getAllBookings() {
//        return bookingRepository.findAll();
//    }
//
//    /**
//     * Crée une réservation après vérification de la disponibilité.
//     * @param newBooking Objet Booking à créer
//     * @return Booking créée et persistée
//     */
//    @Transactional
//    public Booking createBooking(Booking newBooking) {
//        bookingLock.lock();
//        try {
//            // Vérifier que la room existe
//            Room room = roomRepository.findById(newBooking.getRoom().getId())
//                    .orElseThrow(() -> new IllegalArgumentException("room introuvable !"));
//
//            // Vérifier la disponibilité (aucun conflit)
//            if (!isSlotAvailable(room.getId(), newBooking.getStartTime(), newBooking.getEndTime())) {
//                throw new IllegalStateException("Créneau indisponible pour la room " + room.getName());
//            }
//
//            // Enregistrer la réservation
//            return bookingRepository.save(newBooking);
//
//        } finally {
//            bookingLock.unlock();
//        }
//    }
//
//    /**
//     * Vérifie si la room est disponible sur un créneau donné.
//     * @param roomId Identifiant de la room
//     * @param start Date/heure de début
//     * @param end Date/heure de fin
//     * @return true si aucun conflit, false sinon
//     */
//    public boolean isSlotAvailable(Long roomId, LocalDateTime start, LocalDateTime end) {
//        // Récupérer toutes les réservations de la room
//        Optional<Booking> existingBookings = bookingRepository.findByRoomId(roomId);
//
//        // Parcourir pour détecter un conflit d’horaire
//        return existingBookings.stream().noneMatch(booking -> {
//            LocalDateTime sd = booking.getStartTime();
//            LocalDateTime ed = booking.getEndTime();
//            // S’il y a chevauchement, c’est indisponible
//            return !(ed.isBefore(start) || sd.isAfter(end));
//        });
//    }
//
//    /**
//     * Mettre à jour une réservation existante (dates, room, etc.).
//     */
//    @Transactional
//    public Booking updateBooking(Long bookingId, Booking updatedBooking) {
//        // Récupérer la réservation en BD
//        Booking existing = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable !"));
//
//        bookingLock.lock();
//        try {
//            // Vérifier la disponibilité si on change le créneau ou la room
//            if (!isSlotAvailable(updatedBooking.getRoom().getId(),
//                    updatedBooking.getStartTime(),
//                    updatedBooking.getEndTime())) {
//                throw new IllegalStateException("Nouveau créneau indisponible !");
//            }
//            // Appliquer les modifications
//            existing.setRoom(updatedBooking.getRoom());
//            existing.setStartTime(updatedBooking.getStartTime());
//            existing.setEndTime(updatedBooking.getEndTime());
//            // Persister
//            return bookingRepository.save(existing);
//        } finally {
//            bookingLock.unlock();
//        }
//    }
//
//    /**
//     * Annuler (supprimer) une réservation par son id.
//     */
//    @Transactional
//    public void cancelBooking(Long bookingId) {
//        if (!bookingRepository.existsById(bookingId)) {
//            throw new IllegalArgumentException("Impossible d’annuler : booking introuvable !");
//        }
//        bookingRepository.deleteById(bookingId);
//    }
//
//    // Autres méthodes : findByUser, etc.
//}