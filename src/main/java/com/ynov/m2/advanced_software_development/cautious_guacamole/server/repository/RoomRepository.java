package com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findRoomByName(String name);

    List<Room> findAll();

}
