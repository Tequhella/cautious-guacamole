package com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.Booking;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.GuestState;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.State;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;

import jakarta.persistence.EntityManager;

/**
 * Classe de test d'intégration pour le BookingRepository.
 *
 * Elle utilise @DataJpaTest qui :
 *  - Configure une base de données H2 en mémoire,
 *  - Scan les @Entity,
 *  - Crée un contexte Spring restreint aux composants JPA (Repository).
 *
 * EntityManager nous permet de persister les entités avant de tester les méthodes du repository.
 */
@DataJpaTest
@Transactional
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EntityManager entityManager;

    private Room roomA;
    private Room roomB;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        // -- Création de Room
        roomA = new Room();
        roomA.setName("Room A");
        roomA.setSeats(10);
        roomA.setDescription("Petite salle");
        roomA.setEquipments("Projector");

        roomB = new Room();
        roomB.setName("Room B");
        roomB.setSeats(20);
        roomB.setDescription("Grande salle");
        roomB.setEquipments("TV, Whiteboard");

        // -- Création de User
        user1 = new User();
        user1.setName("Alice");
        user1.setEmail("alice@example.com");
        user1.setPassword("pwd");
        user1.setRole("USER");

        user2 = new User();
        user2.setName("Bob");
        user2.setEmail("bob@example.com");
        user2.setPassword("pwd2");
        user2.setRole("USER");

        // On persiste tout ça pour générer leurs IDs
        entityManager.persist(roomA);
        entityManager.persist(roomB);
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();
    }

    @Test
    @DisplayName("Test findByRoomId(...)")
    void testFindByRoomId() {
        // Création d'une réservation (Booking) pour roomA et pour user1
        Booking booking = new Booking();
        booking.setRoom(roomA);
        booking.setStartTime(new Date());
        booking.setEndTime(new Date(System.currentTimeMillis() + 3600_000));

        // Ajout d'un GuestState pour user1
        GuestState guestState = new GuestState();
        guestState.setUser(user1);
        guestState.setState(State.OWNER);
        guestState.setBooking(booking);

        // On ajoute ce GuestState dans la liste du booking
        booking.getGuests().add(guestState);

        // On persiste
        entityManager.persist(booking);
        entityManager.flush();

        // Vérification : findByRoomId(roomA.getId()) doit retourner 1 Booking
        List<Booking> bookings = bookingRepository.findByRoomId(roomA.getId());
        assertEquals(1, bookings.size());
        assertEquals(roomA.getId(), bookings.get(0).getRoom().getId());
    }

    @Test
    @DisplayName("Test findByRoomName(...)")
    void testFindByRoomName() {
        // Création d'une réservation (Booking) pour roomB et pour user2
        Booking booking = new Booking();
        booking.setRoom(roomB);
        booking.setStartTime(new Date());
        booking.setEndTime(new Date(System.currentTimeMillis() + 7200_000));

        // Ajout d'un GuestState pour user2
        GuestState guestState = new GuestState();
        guestState.setUser(user2);
        guestState.setState(State.PENDING);
        guestState.setBooking(booking);

        booking.getGuests().add(guestState);

        // On persiste
        entityManager.persist(booking);
        entityManager.flush();

        // Vérification : findByRoomName("Room B") doit retourner 1 Booking
        List<Booking> bookings = bookingRepository.findByRoomName("Room B");
        assertEquals(1, bookings.size());
        assertEquals("Room B", bookings.get(0).getRoom().getName());
    }

    @Test
    @DisplayName("Test findByUserId(...)")
    void testFindByUserId() {
        // On crée deux Booking, l'un pour user1, l'autre pour user2

        // Booking #1
        Booking booking1 = new Booking();
        booking1.setRoom(roomA);
        booking1.setStartTime(new Date());
        booking1.setEndTime(new Date());

        GuestState gs1 = new GuestState();
        gs1.setUser(user1);
        gs1.setState(State.PENDING);
        gs1.setBooking(booking1);
        booking1.getGuests().add(gs1);

        // Booking #2
        Booking booking2 = new Booking();
        booking2.setRoom(roomB);
        booking2.setStartTime(new Date());
        booking2.setEndTime(new Date());

        GuestState gs2 = new GuestState();
        gs2.setUser(user2);
        gs2.setState(State.PENDING);
        gs2.setBooking(booking2);
        booking2.getGuests().add(gs2);

        // Persister
        entityManager.persist(booking1);
        entityManager.persist(booking2);
        entityManager.flush();

        // Rechercher les Booking liés à user1 (on s’attend à n’en trouver qu’un)
        List<Booking> bookingsUser1 = bookingRepository.findByUserId(user1.getId());
        assertEquals(1, bookingsUser1.size());
        assertTrue(bookingsUser1.stream().allMatch(b -> 
            b.getGuests().stream().anyMatch(g -> g.getUser().getId().equals(user1.getId())))
        );

        // Rechercher les Booking liés à user2 (on s’attend à n’en trouver qu’un)
        List<Booking> bookingsUser2 = bookingRepository.findByUserId(user2.getId());
        assertEquals(1, bookingsUser2.size());
        assertTrue(bookingsUser2.stream().allMatch(b -> 
            b.getGuests().stream().anyMatch(g -> g.getUser().getId().equals(user2.getId())))
        );
    }
}
