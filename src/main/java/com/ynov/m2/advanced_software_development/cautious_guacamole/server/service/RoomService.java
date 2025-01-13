package com.ynov.m2.advanced_software_development.cautious_guacamole.server.service;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    //public Room createRoom(Room room) {
    //    if (room.getName() == null || room.getName().isBlank()) {
    //        throw new IllegalArgumentException("Le nom de la salle est obligatoire");
    //    }
    //    if (roomRepository.findRoomByName(room.getName()).isPresent()) {
    //        throw new IllegalStateException("Une salle avec ce nom existe déjà !");
    //    }
    //    return roomRepository.save(room);
    //}

}
