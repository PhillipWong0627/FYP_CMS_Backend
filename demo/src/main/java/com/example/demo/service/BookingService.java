package com.example.demo.service;


import com.example.demo.Bean.Booking;
import com.example.demo.Bean.Court;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CourtRepository courtRepository;
    @Autowired
    public BookingService(BookingRepository bookingRepository, CourtRepository courtRepository, FacilityRepository facilityRepository, MemberRepository memberRepository) {
        this.bookingRepository = bookingRepository;
        this.courtRepository = courtRepository;
    }

    public boolean checkAvailability(Long courtId, LocalDate date, String timeSlot) {
        List<Booking> bookings = bookingRepository.findByCourt_CourtIdAndDate(courtId, date);
        return bookings.stream().noneMatch(b -> b.getTimeSlot().equals(timeSlot));
    }
    public Booking bookCourt(Long courtId, Long memberId, LocalDate date, String timeSlot) {
        Court court = courtRepository.findById(courtId).orElseThrow(() -> new RuntimeException("Court not found"));
        if (checkAvailability(courtId, date, timeSlot)) {
            Booking booking = new Booking();
            booking.setCourt(court);
            booking.setMemberId(memberId);
            booking.setDate(date);
            booking.setTimeSlot(timeSlot);
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Slot is already booked");
        }
    }




}


