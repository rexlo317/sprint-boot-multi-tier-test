package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.models.ParkingBoyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/parkingboys")
public class ParkingBoyResource {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @GetMapping
    public ResponseEntity<ParkingBoyResponse[]> getAll() {
        final ParkingBoyResponse[] parkingBoys = parkingBoyRepository.findAll().stream()
            .map(ParkingBoyResponse::create)
            .toArray(ParkingBoyResponse[]::new);
        return ResponseEntity.ok(parkingBoys);
    }

    @PostMapping( produces ={"application/json"})
    public ResponseEntity<String> postBoy(@RequestBody ParkingBoy parkingBoy){
        if (parkingBoy.getEmployeeId() == null){
            return ResponseEntity.badRequest().body("Employee Id cannot be null");
        }
        if (parkingBoy.getEmployeeId().length()>10) {
            return ResponseEntity.badRequest().body("Employee Id too long");
        }
        try{
            parkingBoyRepository.save(parkingBoy);
            Long parkingBoyID = parkingBoy.getId();
            return ResponseEntity.created(URI.create("/parkingboys/" + parkingBoyID)).body("A Parking Boy is created!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Id already exists");
        }
    }
}
