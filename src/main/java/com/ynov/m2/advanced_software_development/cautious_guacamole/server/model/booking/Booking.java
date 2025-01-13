package com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;
import jakarta.persistence.*;


@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GuestState> guests = new ArrayList<>();

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Booking(Room room, List<GuestState> guests, LocalDateTime startTime, LocalDateTime endTime) {
        this.room = room;
        this.guests = guests;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Booking(Long id, Room room, List<GuestState> guests, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.room = room;
        this.guests = guests;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Booking() {

    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<GuestState> getGuests() {
        return guests;
    }

    public void setGuests(List<GuestState> guests) {
        this.guests = guests;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "roomName='" + room + '\'' +
                ", bookedBy='" + guests + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
