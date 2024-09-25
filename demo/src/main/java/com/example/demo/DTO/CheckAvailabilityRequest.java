package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CheckAvailabilityRequest {
    private Long courtId;
    private LocalDate date;
    private String timeSlot;

}
