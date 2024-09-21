package com.example.demo.Bean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventTitle;
    private String eventDescription;
    private String eventDateTime;
    private String eventLocation;
    private String eventImage;
    private Double eventFee;
    private Boolean is_active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Many-to-Many relationship with Member
    @ManyToMany
    @JoinTable(
            name = "event_members", // Join table name
            joinColumns = @JoinColumn(name = "event_id"), // Foreign key for Event
            inverseJoinColumns = @JoinColumn(name = "member_id") // Foreign key for Member
    )
    private List<Member> members = new ArrayList<>();

    public Event() {
    }

    public Event(String eventTitle, String eventDescription, String eventDateTime, String eventLocation, String eventImage, Double eventFee, Boolean is_active, LocalDateTime createdAt, LocalDateTime updatedAt, List<Member> members) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDateTime = eventDateTime;
        this.eventLocation = eventLocation;
        this.eventImage = eventImage;
        this.eventFee = eventFee;
        this.is_active = is_active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.members = members;
    }

    // Automatically set createdAt before persisting
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Automatically update updatedAt before update
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
