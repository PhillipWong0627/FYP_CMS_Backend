package com.example.demo.Bean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private LocalDate date;
    private String timeSlot;

    @ManyToOne
    @JoinColumn(name = "courtId")
    private Court court;

    private Long memberId;  // ID of the user who booked the court

    public Booking() {
    }

    public Booking(Long memberId, Court court, String timeSlot, LocalDate date) {
        this.memberId = memberId;
        this.court = court;
        this.timeSlot = timeSlot;
        this.date = date;
    }
}
