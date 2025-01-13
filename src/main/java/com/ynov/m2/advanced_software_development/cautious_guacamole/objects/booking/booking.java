package com.ynov.m2.advanced_software_development.cautious_guacamole.objects.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GuestState> guests = new ArrayList<>();

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Booking(String roomName, List<GuestState> guests, LocalDateTime startTime, LocalDateTime endTime) {
        this.roomName = roomName;
        this.guests = guests;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Booking(Long id, String roomName, List<GuestState> guests, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.roomName = roomName;
        this.guests = guests;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
                "roomName='" + roomName + '\'' +
                ", bookedBy='" + guests + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
