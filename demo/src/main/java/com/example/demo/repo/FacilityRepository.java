package com.example.demo.repo;

import com.example.demo.Bean.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacilityRepository  extends JpaRepository<Facility, Long> {
    // Custom queries can be added here if necessary

}
