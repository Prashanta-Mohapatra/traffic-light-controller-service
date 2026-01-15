package com.natwest.kata.api;

import com.natwest.kata.model.Direction;
import com.natwest.kata.service.IntersectionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrafficController.class)
class TrafficControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IntersectionService service;

    // ---------- GREEN ----------

    @Test
    @DisplayName("POST /traffic/green/{direction} sets GREEN for a valid direction")
    void setGreenForDirection() throws Exception {
        doNothing().when(service).green(Direction.NORTH_SOUTH);

        mockMvc.perform(post("/traffic/green/NORTH_SOUTH"))
                .andExpect(status().isOk());
    }

    // ---------- YELLOW ----------

    @Test
    @DisplayName("POST /traffic/yellow/{direction} sets YELLOW for a valid direction")
    void setYellowForDirection() throws Exception {
        doNothing().when(service).yellow(Direction.EAST_WEST);

        mockMvc.perform(post("/traffic/yellow/EAST_WEST"))
                .andExpect(status().isOk());
    }

    // ---------- RED ----------

    @Test
    @DisplayName("POST /traffic/red/{direction} sets RED for a valid direction")
    void setRedForDirection() throws Exception {
        doNothing().when(service).red(Direction.NORTH_SOUTH);

        mockMvc.perform(post("/traffic/red/NORTH_SOUTH"))
                .andExpect(status().isOk());
    }

    // ---------- PAUSE ----------

    @Test
    @DisplayName("POST /traffic/pause pauses traffic light operation")
    void pauseTrafficLights() throws Exception {
        doNothing().when(service).pause();

        mockMvc.perform(post("/traffic/pause"))
                .andExpect(status().isOk());
    }

    // ---------- RESUME ----------

    @Test
    @DisplayName("POST /traffic/resume resumes traffic light operation")
    void resumeTrafficLights() throws Exception {
        doNothing().when(service).resume();

        mockMvc.perform(post("/traffic/resume"))
                .andExpect(status().isOk());
    }

    // ---------- CURRENT STATE ----------

    @Test
    @DisplayName("GET /traffic/state returns current traffic light state")
    void getCurrentState() throws Exception {
        mockMvc.perform(get("/traffic/state"))
                .andExpect(status().isOk());
    }

    // ---------- HISTORY ----------

    @Test
    @DisplayName("GET /traffic/history returns traffic light timing history")
    void getHistory() throws Exception {
        mockMvc.perform(get("/traffic/history"))
                .andExpect(status().isOk());
    }

    // ---------- INVALID DIRECTION ----------

    @Test
    @DisplayName("Invalid direction results in HTTP 400 Bad Request")
    void invalidDirectionReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/traffic/green/INVALID"))
                .andExpect(status().isBadRequest());
    }
}
