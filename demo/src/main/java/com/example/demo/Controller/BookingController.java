package com.example.demo.Controller;

import com.example.demo.Bean.Booking;
import com.example.demo.DTO.BookCourtRequest;
import com.example.demo.DTO.CheckAvailabilityRequest;
import com.example.demo.result.Result;
import com.example.demo.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/member/bookings")
public class BookingController {
    private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/check")
    public boolean checkAvailability(@RequestBody CheckAvailabilityRequest request) {
        return bookingService.checkAvailability(request.getCourtId(), request.getDate(), request.getTimeSlot());
    }

    // Book court using @RequestBody
    @PostMapping("/create")
    public Booking bookCourt(@RequestBody BookCourtRequest request) {
        return bookingService.bookCourt(request.getCourtId(), request.getMemberId(), request.getDate(), request.getTimeSlot());
    }



//    @PostMapping("/create")
//    public ResponseEntity<Result<Boolean>> createBooking(
//            @RequestParam Long memberId,
//            @RequestParam Long facilityId,
//            @RequestParam Long courtId,
//            @RequestParam String bookingStart,
//            @RequestParam int duration) {
//
//        // Convert the bookingStart to LocalDateTime if necessary
//        LocalDateTime startDateTime = LocalDateTime.parse(bookingStart);
//        System.out.println(memberId);
//        System.out.println(facilityId);
//        System.out.println(courtId);
//        System.out.println(startDateTime);
//        System.out.println(duration);
//        Result<Boolean> result = bookingService.createBooking(memberId, facilityId, courtId, startDateTime, duration);
//        if (result.getCode() == 0) {
//            return ResponseEntity.ok(result);
//        } else {
//            return ResponseEntity.status(400).body(result);
//        }
//    }
//    @PostMapping("/create/param")
//    public ResponseEntity<Result<Boolean>> createBooking(
//            @RequestBody Booking booking, @RequestParam int duration) {
////        LocalDateTime startTime = LocalDateTime.parse(bookingStart);
//        System.out.println(booking.getMember().getId());
//        System.out.println(booking.getFacility().getFacilityID());
//        System.out.println(booking.getCourt().getId());
//        System.out.println(booking.getBookingStart());
//        System.out.println(duration);
//        Result<Boolean> result = bookingService.createBooking(booking.getMember().getId(), booking.getFacility().getFacilityID(), booking.getCourt().getId(), booking.getBookingStart(), duration);
//        if (result.getCode() == 0) {
//            return ResponseEntity.ok(result);
//        } else {
//            return ResponseEntity.status(400).body(result);
//        }
//    }

//    @GetMapping("/{memberId}")
//    public ResponseEntity<Result<List<Booking>>> getBookingsByMember(@PathVariable Long memberId) {
//        List<Booking> bookings = bookingService.getBookingsByMember(memberId);
//        return ResponseEntity.ok(Result.success(bookings));
//    }

//    @PostMapping("/cancel/{bookingId}")
//    public ResponseEntity<Result<Boolean>> cancelBooking(@PathVariable Long bookingId) {
//        Result<Boolean> result = bookingService.cancelBooking(bookingId);
//        if (result.getCode() == 0) {
//            return ResponseEntity.ok(result);
//        } else {
//            return ResponseEntity.status(400).body(result);
//        }
//    }

}