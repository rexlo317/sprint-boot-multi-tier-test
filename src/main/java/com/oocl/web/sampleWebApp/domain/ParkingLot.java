package com.oocl.web.sampleWebApp.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "parking_lot_id", length = 10, unique = true, nullable = false)
    private String parkingLotId;

    @Column(name = "capacity", length = 3, nullable = false)
    private int capacity;

    @Column(name = "available_position_count", length = 3, nullable = false)
    private int availablePositionCount;

    @Column(name = "parking_boy_id", length = 10)
    private Long parkingBoyId;

    public Long getParkingBoyId() {
        return parkingBoyId;
    }

    public void setParkingBoyId(Long parkingBoyId) {
        this.parkingBoyId = parkingBoyId;
    }

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

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
