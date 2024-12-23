package com.example.demo.repo;

import com.example.demo.Bean.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByMemberId(Long memberId);
    List<Booking> findByCourt_CourtIdAndDate(Long courtId, LocalDate date);

    List<Booking> findByFacilityId(Long facilityId);
    //GGG
    List<Booking> findByFacilityIdAndDate(Long facilityId, LocalDate date);

    boolean existsByCourt_CourtIdAndDateAndTimeSlot(Long courtId, LocalDate date, String timeSlot);
}
