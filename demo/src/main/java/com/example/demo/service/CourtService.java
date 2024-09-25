package com.example.demo.service;

import com.example.demo.Bean.Court;
import com.example.demo.repo.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourtService {
    private final CourtRepository courtRepository;

    @Autowired
    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    // Get all courts by Facility ID
    public List<Court> getCourtsByFacility(Long facilityId) {
        return courtRepository.findByFacility_FacilityID(facilityId);
    }

    // Book a court: Check if the court is available and then book it
    public boolean bookCourt(Long courtId, String bookingStart, int duration) {
        Optional<Court> courtOptional = courtRepository.findById(courtId);
        if (courtOptional.isPresent()) {
            Court court = courtOptional.get();
            if (court.getAvailable()) {
                // Assume booking logic is here
                court.setAvailable(false);  // Set court to unavailable after booking
                courtRepository.save(court);
                return true;  // Booking successful
            }
        }
        return false;  // Court is not available
    }

    // Update court availability
    public boolean updateCourtAvailability(Long courtId, boolean available) {
        Optional<Court> courtOptional = courtRepository.findById(courtId);
        if (courtOptional.isPresent()) {
            Court court = courtOptional.get();
            court.setAvailable(available);
            courtRepository.save(court);
            return true;
        }
        return false;
    }

}
