package com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.room.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GuestState> guests = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    public Booking(Room room, List<GuestState> guests, Date startTime, Date endTime) {
        this.room = room;
        this.guests = guests;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Booking() {

    }

}
