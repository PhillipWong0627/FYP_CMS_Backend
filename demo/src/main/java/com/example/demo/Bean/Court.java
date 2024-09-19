package com.example.demo.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courtNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    @JsonIgnore
    private Facility facility;
    @Column(nullable = false)
    private Boolean available = true;  // Default to true, meaning available by default

    public Court() {
    }

    public Court(String courtNumber, Facility facility,Boolean available) {
        this.courtNumber = courtNumber;
        this.facility = facility;
        this.available = available;

    }

}
