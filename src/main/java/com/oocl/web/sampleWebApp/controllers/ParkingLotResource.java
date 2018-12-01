package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
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
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @GetMapping
    public ResponseEntity<ParkingLotResponse[]> getAll() {
        final ParkingLotResponse[] parkingLots = parkingLotRepository.findAll().stream()
                .map(ParkingLotResponse::create)
                .toArray(ParkingLotResponse[]::new);
        return ResponseEntity.ok(parkingLots);
    }

    @PostMapping(produces ={"application/json"})
    public ResponseEntity<String> postLot(@RequestBody ParkingLot parkingLot){
        if (parkingLot.getCapacity()<1 || parkingLot.getCapacity()>100)
            return ResponseEntity.badRequest().body("Capacity not in range");
        if (parkingLot.getParkingLotId().length()>10)
            return ResponseEntity.badRequest().body("Parking Lot Id too long");
        parkingLot.setAvailablePositionCount(parkingLot.getCapacity());
        parkingLotRepository.save(parkingLot);
        Long parkingLotID = parkingLot.getId();
        return ResponseEntity.created(URI.create("/parkinglots/" + parkingLotID)).body("A Parking Lot is created!");

    }

    @PutMapping(path = "/{parkingLotId}/parkingBoy/{parkingBoyId}",produces = {"application/json"})
    public ResponseEntity<String> putLot(@PathVariable Long parkingLotId, @PathVariable Long parkingBoyId){
        ParkingBoy parkingBoy = parkingBoyRepository.findById(parkingBoyId).get();
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).get();
        parkingBoy.assignLot(parkingLot);
        parkingLot.setParkingBoyId(parkingBoyId);
        parkingBoyRepository.save(parkingBoy);
        parkingLotRepository.save(parkingLot);
        return ResponseEntity.ok().body("Assigned to boy");
    }
}