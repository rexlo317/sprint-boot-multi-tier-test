package com.oocl.web.sampleWebApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.web.sampleWebApp.domain.*;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import com.oocl.web.sampleWebApp.models.ParkingLotResponse;
import com.oocl.web.sampleWebApp.models.ParkingLotResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.oocl.web.sampleWebApp.WebTestUtil.getContentAsObject;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotTest {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void should_get_parking_lots() throws Exception {
        // Given
        final ParkingLot lot = parkingLotRepository.save(new ParkingLot("lot",100));

        // When
        final MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/parkinglots"))
                .andReturn();

        // Then
        assertEquals(200, result.getResponse().getStatus());

        final ParkingLotResponse[] parkingLots = getContentAsObject(result, ParkingLotResponse[].class);

        assertEquals(1, parkingLots.length);
        assertEquals("lot", parkingLots[0].getParkingLotId());
    }

    @Test
    public void should_post_parking_lots() throws Exception {
        // Given
        ParkingLot parkingLot = new ParkingLot("1", 100);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(parkingLot);
        // When
        final MvcResult result = mvc.perform(post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        ParkingLot actualLot = parkingLotRepository.findAll().get(0);
        assertEquals(201, result.getResponse().getStatus());

        assertEquals("1", actualLot.getParkingLotId());
        assertEquals(100, actualLot.getCapacity());
    }


    @Test
    public void should_return_400_bad_request_id_too_long() throws Exception {
        // Given
        ParkingLot longIDParkingLot = new ParkingLot("12345678901",100);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(longIDParkingLot);
        // When
        final MvcResult result = mvc.perform(post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        // Then
        boolean hasLot = parkingLotRepository.findAll().equals(longIDParkingLot);
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Parking Lot Id too long", result.getResponse().getContentAsString());
        assertEquals(false, hasLot);
    }
    @Test
    public void should_return_400_bad_request_id_cannot_be_null() throws Exception {
        // Given
        ParkingLot nullIDParkingLot = new ParkingLot(null,100);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(nullIDParkingLot);
        // When
        final MvcResult result = mvc.perform(post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        // Then
        boolean hasLot = parkingLotRepository.findAll().equals(nullIDParkingLot);
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Parking Lot Id cannot be null", result.getResponse().getContentAsString());
        assertEquals(false, hasLot);
    }
    @Test
    public void should_return_400_bad_request_capacity_not_in_range() throws Exception {
        // Given
        ParkingLot notInRangeParkingLot = new ParkingLot(null,1000);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(notInRangeParkingLot);
        // When
        final MvcResult result = mvc.perform(post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        // Then
        boolean hasLot = parkingLotRepository.findAll().equals(notInRangeParkingLot);
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Capacity not in range", result.getResponse().getContentAsString());
        assertEquals(false, hasLot);
    }
    @Test
    public void should_return_400_bad_request_id_already_exist() throws Exception {
        // Given
        ParkingLot sameIdParkingLot = new ParkingLot("123",100);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(sameIdParkingLot);
        // When
        final MvcResult result = mvc.perform(post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andReturn();
        final MvcResult sameIdResult = mvc.perform(post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andReturn();
        // Then
        assertEquals(400, sameIdResult.getResponse().getStatus());
        assertEquals("Id already exists", sameIdResult.getResponse().getContentAsString());
    }
}
