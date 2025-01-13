package com.ynov.m2.advanced_software_development.cautious_guacamole.objects.booking;

import java.time.LocalDateTime;
import java.util.HashMap;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.ynov.m2.advanced_software_development.cautious_guacamole.objects.user.User;


@Entity
@Table(name = "booking")
public class booking {
    private String roomName;
    private HashMap<User, State> guests;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public booking(String roomName, HashMap<User, State> guests, LocalDateTime startTime, LocalDateTime endTime) {
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

    public HashMap<User, State> getGuests() {
        return guests;
    }

    public void setGuests(HashMap<User, State> guests) {
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
