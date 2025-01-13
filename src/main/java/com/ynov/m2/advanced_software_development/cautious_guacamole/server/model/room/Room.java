package com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int seats;

    private String description;

    private Equipment equipements;

    public Room(Long id, String name, int seats, String description, Equipment equipements) {
        this.id = id;
        this.name = name;
        this.seats = seats;
        this.description = description;
        this.equipements = equipements;
    }

    public Room() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getEquipements() {
        return equipements;
    }

    public void setEquipements(List<String> equipements) {
        this.equipements = equipements;
    }

}
