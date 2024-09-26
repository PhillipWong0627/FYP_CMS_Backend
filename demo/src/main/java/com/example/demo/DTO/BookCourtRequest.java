package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookCourtRequest {
    private Long courtId;
    private Long memberId;
    private LocalDate date;
    private String timeSlot;
    private Long facilityID;

}


