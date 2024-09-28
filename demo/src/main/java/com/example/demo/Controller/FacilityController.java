package com.example.demo.Controller;


import com.example.demo.Bean.Court;
import com.example.demo.Bean.Facility;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.CourtService;
import com.example.demo.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/admin")
public class FacilityController {

    private final FacilityService facilityService;
    private final CourtService courtService;

    @Autowired
    public FacilityController(FacilityService facilityService, CourtService courtService) {
        this.facilityService = facilityService;
        this.courtService = courtService;
    }

    @PostMapping("/addFacilities")
    public Result<Facility> createFacility(@RequestBody Facility facility) {
        List<Court> courts = new ArrayList<>();
        for (int i = 1; i <= facility.getTotalCourt(); i++) {
            Court court = new Court();
            court.setCourtNumber("Court " + i);  // Set court number as "Court 1", "Court 2", etc.
            court.setAvailable(true);             // Set default available status (or modify as needed)
            court.setFacility(facility);          // Associate court with the facility
            courts.add(court);
        }
        facility.setCourts(courts);  // Add courts to the facility

//        for (Court court : facility.getCourts()) {
//            court.setFacility(facility); // Set facility for each court
//        }
        Facility savedFacility = facilityService.createFacility(facility); // Save facility and associated courts
            return Result.success(savedFacility);
//        return facilityService.createFacility(facility);
        //TEST
    }

    @GetMapping("/getFacilities")
    public Result<List<Facility>> getAllFacilities() {
        List<Facility> facilities = facilityService.getFacility();
        return Result.success(facilities);

    }

    @GetMapping("getById/{id}")
    public Result<Optional<Facility>> getFacilityById(@PathVariable Long id) {
        Optional<Facility> getFacilityById = facilityService.getFacilityById(id);

        if(getFacilityById.isPresent()){

            return Result.success(getFacilityById);
        }
            return Result.error(CodeMsg.FACILITY_NOT_EXIST);
    }

    @PutMapping("updateById/{id}")
    public Result<Facility> updateFacility(@PathVariable Long id, @RequestBody Facility facility) {
        try {
            Facility updatedFacility = facilityService.updateFacility(id, facility);
            return Result.success(updatedFacility);
        } catch (RuntimeException e) {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    // Delete a facility
    @DeleteMapping("deleteById/{id}")
    public Result<Boolean> deleteFacility(@PathVariable Long id) {
        try{
            Result<Boolean> isDeleted = facilityService.deleteFacility(id);
            if(isDeleted.getData()){
                return Result.success(true);
            }else{
                return Result.error(CodeMsg.FACILITY_NOT_EXIST);
            }
        } catch (Exception e){
            return Result.error(CodeMsg.SERVER_ERROR.fillArgs("Failed to delete member"));
        }

    }

    @GetMapping("/facilities/{facilityId}/courts")
    public Result<List<Court>> getCourtsByFacility(@PathVariable Long facilityId) {
        List<Court> courts = courtService.getCourtsByFacility(facilityId);
        return Result.success(courts);
    }



}

