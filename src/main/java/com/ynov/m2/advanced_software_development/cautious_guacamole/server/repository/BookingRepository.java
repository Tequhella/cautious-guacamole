package com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoomId(Long roomId);

    List<Booking> findByRoomName(String name);

    List<Booking> findByUserId(Long userId);



}
