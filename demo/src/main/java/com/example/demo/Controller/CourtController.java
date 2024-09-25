package com.example.demo.Controller;


import com.example.demo.Bean.Court;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.CourtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courts")
public class CourtController {
    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }
    // Get all courts by Facility ID
    @GetMapping("/facility/{facilityId}")
    public Result<List<Court>> getCourtsByFacility(@PathVariable Long facilityId) {
        try {
            List<Court> courts = courtService.getCourtsByFacility(facilityId);
            return Result.success(courts);
        } catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR.fillArgs("Failed to retrieve courts."));
        }
    }

    // Book a court
    @PostMapping("/book")
    public Result<Boolean> bookCourt(@RequestParam Long courtId, @RequestParam String bookingStart, @RequestParam int duration) {
        try {
            boolean isBooked = courtService.bookCourt(courtId, bookingStart, duration);
            if (isBooked) {
                return Result.success(true);
            } else {
                return Result.error(CodeMsg.COURT_UNAVAILABLE.fillArgs("Court is unavailable."));
            }
        } catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR.fillArgs("Booking failed."));
        }
    }

    // Mark a court as available/unavailable
    @PatchMapping("/updateAvailability/{courtId}")
    public Result<Boolean> updateCourtAvailability(@PathVariable Long courtId, @RequestParam boolean available) {
        try {
            boolean updated = courtService.updateCourtAvailability(courtId, available);
            return Result.success(updated);
        } catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR.fillArgs("Failed to update court availability."));
        }
    }

}
