package com.oocl.web.sampleWebApp.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Entity
@Table(name = "parking_boy")
public class ParkingBoy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ArrayList<ParkingLot> parkingLots = new ArrayList<ParkingLot>();

    @Column(name = "employee_id", length = 10, unique = true, nullable = false)
    private String employeeId;

    public Long getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    protected ParkingBoy() {}

    public ParkingBoy(String employeeId, ArrayList<ParkingLot> parkingLots) {
        this.employeeId = employeeId;
        this.parkingLots = parkingLots;
    }

    public void assignLot(ParkingLot parkingLot){
        parkingLots.add(parkingLot);
    }

    public ArrayList<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(ArrayList<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

}

