package com.example.demo.Controller;


import com.example.demo.Bean.Court;
import com.example.demo.Bean.Facility;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/admin")
public class FacilityController {

    private final FacilityService facilityService;

    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @PostMapping("/addFacilities")
    public Result<Facility> createFacility(@RequestBody Facility facility) {
        for (Court court : facility.getCourts()) {
            court.setFacility(facility); // Set facility for each court
        }
        Facility savedFacility = facilityService.createFacility(facility); // Save facility and associated courts
            return Result.success(facility);
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
    public ResponseEntity<Facility> updateFacility(@PathVariable Long id, @RequestBody Facility facility) {
        try {
            Facility updatedFacility = facilityService.updateFacility(id, facility);
            return ResponseEntity.ok(updatedFacility);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a facility
    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.noContent().build();
    }


}

