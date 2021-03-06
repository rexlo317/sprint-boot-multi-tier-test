package com.oocl.web.sampleWebApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import com.oocl.web.sampleWebApp.models.ParkingBoyResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.oocl.web.sampleWebApp.WebTestUtil.getContentAsObject;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingBoyTest {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private MockMvc mvc;

	@Test
	public void should_get_parking_boys() throws Exception {
	    // Given
        final ParkingBoy boy = parkingBoyRepository.save(new ParkingBoy("boy", null));

        // When
        final MvcResult result = mvc.perform(MockMvcRequestBuilders
            .get("/parkingboys"))
            .andReturn();

        // Then
        assertEquals(200, result.getResponse().getStatus());

        final ParkingBoyResponse[] parkingBoys = getContentAsObject(result, ParkingBoyResponse[].class);

        assertEquals(1, parkingBoys.length);
        assertEquals("boy", parkingBoys[0].getEmployeeId());
    }

    @Test
    public void should_post_parking_boys() throws Exception {
        // Given
        ParkingBoy parkingBoy = new ParkingBoy("1",null);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(parkingBoy);
        // When
        final MvcResult result = mvc.perform(post("/parkingboys")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonContent))
                                    .andDo(print())
                                    .andExpect(status().isCreated())
                                    .andReturn();

        // Then
        ParkingBoy actualBoy = parkingBoyRepository.findAll().get(0);
        assertEquals(201, result.getResponse().getStatus());
        assertEquals("1", actualBoy.getEmployeeId());
    }

    @Test
    public void should_return_400_bad_request_id_cannot_be_null() throws Exception {
        // Given
        ParkingBoy longIDParkingBoy = new ParkingBoy(null, null);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(longIDParkingBoy);
        // When
        final MvcResult result = mvc.perform(post("/parkingboys")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonContent))
                                    .andDo(print())
                                    .andExpect(status().isBadRequest())
                                    .andReturn();
        // Then
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Employee Id cannot be null", result.getResponse().getContentAsString());
    }

    @Test
    public void should_return_400_bad_request_id_too_long() throws Exception {
        // Given
        ParkingBoy longIDParkingBoy = new ParkingBoy("12345678901", null);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(longIDParkingBoy);
        // When
        final MvcResult result = mvc.perform(post("/parkingboys")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonContent))
                                    .andDo(print())
                                    .andExpect(status().isBadRequest())
                                    .andReturn();
        // Then
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Employee Id too long", result.getResponse().getContentAsString());
    }

    @Test
    public void should_return_400_bad_request_id_already_exist() throws Exception {
        // Given
        ParkingBoy sameIDParkingBoy = new ParkingBoy("123", null);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(sameIDParkingBoy);
        // When
        final MvcResult result = mvc.perform(post("/parkingboys")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonContent))
                                    .andReturn();
        final MvcResult sameIdResult = mvc.perform(post("/parkingboys")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonContent))
                                    .andReturn();
        // Then
        assertEquals(400, sameIdResult.getResponse().getStatus());
        assertEquals("Id already exists", sameIdResult.getResponse().getContentAsString());
    }
}
