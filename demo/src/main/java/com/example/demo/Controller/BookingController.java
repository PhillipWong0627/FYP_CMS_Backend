package com.example.demo.Controller;

import com.example.demo.Bean.Booking;
import com.example.demo.Bean.Member;
import com.example.demo.DTO.BookCourtRequest;
import com.example.demo.DTO.CheckAvailabilityRequest;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/member/bookings")
public class BookingController {
    private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    //Get All Booking List
    @GetMapping("/getBooking-List")
    public Result<List<Booking>> getBookingList(){
        try{
            List<Booking> bookings = bookingService.getBookingList();
            return Result.success(bookings);
        } catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    @GetMapping("/getBooking-ListByFacId")
    public Result<List<Booking>> getBookingListByFacId(@RequestParam("facId") Long facId){
        try{
            List<Booking> bookings = bookingService.getBookingListByFaId(facId);
            return Result.success(bookings);

        }catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
    @GetMapping("/getListByFacilityId_Date")
    public Result<List<Booking>> getBookings(@RequestParam Long facilityId, @RequestParam LocalDate date) {
        try{
            List<Booking> bookings = bookingService.getBookingsByFacilityAndDate(facilityId, date);
            return Result.success(bookings);

        } catch (Exception e) {
            return Result.error(CodeMsg.ALREADY_REDEEMED);
        }
    }
    @GetMapping("/getListByMemberId")
    public Result<List<Booking>> getBookingsByMemberId(@RequestParam Long memberId) {
        try{
            List<Booking> bookings = bookingService.getBookingByMemberId(memberId);
            return Result.success(bookings);

        } catch (Exception e) {
            return Result.error(CodeMsg.ALREADY_REDEEMED);
        }
    }

    @PostMapping("/check")
    public Result<Boolean> checkAvailability(@RequestBody CheckAvailabilityRequest request) {
        boolean isAvailable = bookingService.checkAvailability(request.getCourtId(), request.getDate(), request.getTimeSlot());
        if(isAvailable){
            return Result.success(true);
        }else{
            return Result.success(false);
        }
    }

    // Book court using @RequestBody
    @PostMapping("/create")
    public Result<Booking> bookCourt(@RequestBody BookCourtRequest request) {
        try{
            Booking updatedMember = bookingService.bookCourt(request.getCourtId(), request.getMemberId(), request.getDate(), request.getTimeSlot(), request.getFacilityID());
//            System.out.println(updatedMember);
            return Result.success(updatedMember);
        } catch (RuntimeException e) {
            return Result.error(CodeMsg.COURT_NOT_AVAILABLE);
        }
    }

    @GetMapping("/findBookingById/{id}")
    public Result<Optional<Booking>> findBookingById(@PathVariable() Long id){
        Optional<Booking> getBookingById = bookingService.getBookingByBookingId(id);
        if(getBookingById.isPresent()){
            return Result.success(getBookingById);
        }
        return Result.error(CodeMsg.SERVER_ERROR);
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
