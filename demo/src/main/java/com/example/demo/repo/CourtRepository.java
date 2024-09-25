package com.example.demo.repo;

import com.example.demo.Bean.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    // Custom query to find courts by Facility ID
    List<Court> findByFacility_FacilityID(Long facilityId);

}
