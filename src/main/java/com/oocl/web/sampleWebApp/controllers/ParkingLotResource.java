package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import com.oocl.web.sampleWebApp.models.ParkingLotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/parkinglots")
public class ParkingLotResource {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @GetMapping
    public ResponseEntity<ParkingLotResponse[]> getAll() {
        final ParkingLotResponse[] parkingLots = parkingLotRepository.findAll().stream()
                .map(ParkingLotResponse::create)
                .toArray(ParkingLotResponse[]::new);
        return ResponseEntity.ok(parkingLots);
    }

    @PostMapping( produces ={"application/json"})
    public ResponseEntity<String> postLot(@RequestBody ParkingLot parkingLot){
        try {
            parkingLotRepository.save(parkingLot);
            Long parkingLotID = parkingLot.getId();
            return ResponseEntity.created(URI.create("/parkinglots/" + parkingLotID)).body("A Parking Lot is created!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("JSON input error");
        }
    }
}