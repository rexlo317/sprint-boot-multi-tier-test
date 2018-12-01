package com.oocl.web.sampleWebApp.domain;

import javax.persistence.*;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "parkinglot_id", length = 10, unique = true, nullable = false)
    private String parkingLotId;

    @Column(name = "capacity", length = 3, nullable = false)
    private int capacity;

    @Column(name = "availablePositionCount", length = 3, nullable = false)
    private int availablePositionCount;

    public Long getId() {
        return id;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String employeeId) {
        this.parkingLotId = employeeId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailablePositionCount() {
        return availablePositionCount;
    }

    public void setAvailablePositionCount(int availablePositionCount) {
        this.availablePositionCount = availablePositionCount;
    }

    protected ParkingLot() {}


    public ParkingLot(String parkingLotId, int capacity) {
        this.parkingLotId = parkingLotId;
        this.capacity = capacity;
        this.availablePositionCount = capacity;
    }
}
