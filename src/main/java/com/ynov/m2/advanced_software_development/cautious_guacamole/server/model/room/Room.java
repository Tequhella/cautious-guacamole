package com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int seats;

    private String description;

    private String equipments;

    public Room(String name, int seats, String description, String equipments) {
        this.id = id;
        this.name = name;
        this.seats = seats;
        this.description = description;
        this.equipments = equipments;
    }

    public Room() {

    }

}
