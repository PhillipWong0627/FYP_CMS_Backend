package com.example.demo.service;

import com.example.demo.Bean.Facility;
import com.example.demo.repo.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {
    private final FacilityRepository facilityRepository;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public Facility createFacility(Facility facility) {
        return facilityRepository.save(facility);
    }

    public List<Facility> getFacility(){
        return facilityRepository.findAll();
    }

    public Optional<Facility> getFacilityById(Long id){
        return facilityRepository.findById(id);
    }

    // Update an existing facility
    public Facility updateFacility(Long id, Facility updatedFacility) {
        return facilityRepository.findById(id).map(facility -> {
            facility.setFacilityName(updatedFacility.getFacilityName());
            facility.setFacilityImage(updatedFacility.getFacilityImage());
            facility.setPricePerHour(updatedFacility.getPricePerHour());
            facility.setTotalCourt(updatedFacility.getTotalCourt());
            facility.setLocation(updatedFacility.getLocation());
            facility.setOpeningHour(updatedFacility.getOpeningHour());
            facility.setClosingHour(updatedFacility.getClosingHour());
            facility.setFacilityType(updatedFacility.getFacilityType());
            facility.setStatus(updatedFacility.getStatus());
            return facilityRepository.save(facility);
        }).orElseThrow(() -> new RuntimeException("Facility not found with id " + id));
    }
    public void deleteFacility(Long id) {
        facilityRepository.deleteById(id);
    }

}


