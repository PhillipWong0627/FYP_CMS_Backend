package com.example.demo.service;

import com.example.demo.Bean.Court;
import com.example.demo.Bean.Facility;
import com.example.demo.repo.FacilityRepository;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final CourtService courtService;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository, CourtService courtService) {
        this.facilityRepository = facilityRepository;
        this.courtService = courtService;
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

            // Handle court updates based on totalCourt value
            int currentCourtCount = facility.getCourts().size();
            int newTotalCourt = updatedFacility.getTotalCourt();

            if (newTotalCourt > currentCourtCount) {
                // Add new courts if the new totalCourt is greater than the current number
                for (int i = currentCourtCount + 1; i <= newTotalCourt; i++) {
                    Court newCourt = new Court();
                    newCourt.setCourtNumber("Court " + i);
                    newCourt.setAvailable(true); // Set default availability
                    newCourt.setFacility(facility); // Associate court with the facility
                    facility.getCourts().add(newCourt);
                }
            } else if (newTotalCourt < currentCourtCount) {
                // Remove courts if the new totalCourt is less than the current number
                List<Court> courts = facility.getCourts();
                for (int i = currentCourtCount; i > newTotalCourt; i--) {
                    Court courtToRemove = courts.get(i - 1); // Get the last court
                    courts.remove(courtToRemove); // Remove court from list
                    courtService.deleteCourtById(courtToRemove.getCourtId()); // Optionally delete court from DB
                }
            }

            return facilityRepository.save(facility);
        }).orElseThrow(() -> new RuntimeException("Facility not found with id " + id));
    }
    public Result<Boolean> deleteFacility(Long id) {
        Optional<Facility> facilityOptional = facilityRepository.findById(id);
        if(facilityOptional.isPresent()) {
            facilityRepository.deleteById(id);
            return Result.success(true);
        }
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}


