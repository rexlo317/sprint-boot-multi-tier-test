package com.oocl.web.sampleWebApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oocl.web.sampleWebApp.domain.ParkingLot;

import java.util.Objects;

public class ParkingLotResponse {
    private String parkingLotId;
    private int capacity;

    public Long getParkingBoyId() {
        return parkingBoyId;
    }

    public void setParkingBoyId(Long parkingBoyId) {
        this.parkingBoyId = parkingBoyId;
    }

    private Long parkingBoyId;


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public static ParkingLotResponse create(String parkingLotId, int capacity) {
        Objects.requireNonNull(parkingLotId);
        Objects.requireNonNull(capacity);

        final ParkingLotResponse response = new ParkingLotResponse();
        response.setParkingLotId(parkingLotId);
        response.setCapacity(capacity);
        return response;
    }

    public static ParkingLotResponse create(String parkingLotId, int capacity, Long parkingBoyId) {
        Objects.requireNonNull(parkingLotId);
        Objects.requireNonNull(capacity);

        final ParkingLotResponse response = new ParkingLotResponse();
        response.setParkingLotId(parkingLotId);
        response.setCapacity(capacity);
        response.setParkingBoyId(parkingBoyId);
        return response;
    }

    public static ParkingLotResponse create(ParkingLot entity) {
        if (entity.getParkingBoyId() == null)
            return create(entity.getParkingLotId(), entity.getCapacity());
        return create(entity.getParkingLotId(),entity.getCapacity(),entity.getParkingBoyId());
    }

    @JsonIgnore
    public boolean isValid() {
        return parkingLotId != null;
    }
}
