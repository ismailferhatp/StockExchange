package com.ing.stockexchange.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.stockexchange.controller.StockController;
import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }

    @Test
    void testCreateStock() throws Exception {
        // Create a StockDTO object
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setName("New Stock");

        // Mock the service call
        when(stockService.createStock(any(StockDTO.class))).thenReturn(stockDTO);

        // Convert the stockDTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String stockDTOJson = objectMapper.writeValueAsString(stockDTO);

        // Perform the POST request and capture the result
        MvcResult result = mockMvc.perform(post("/api/v1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockDTOJson))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response JSON
        String responseJson = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseJson, Map.class);

        // Extract the "data" field from the response
        Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
        StockDTO returnedStockDTO = objectMapper.convertValue(dataMap, StockDTO.class);

        // Validate the result
        assertEquals("New Stock", returnedStockDTO.getName());
    }


    @Test
     void testUpdateStock() throws Exception {
        // Create a StockDTO object
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setName("Updated Stock");

        // Mock the service call
        when(stockService.updateStock(any(StockDTO.class))).thenReturn(stockDTO);

        // Convert the stockDTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String stockDTOJson = objectMapper.writeValueAsString(stockDTO);

        // Perform the PUT request and capture the result
        MvcResult result = mockMvc.perform(put("/api/v1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockDTOJson))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response JSON
        String responseJson = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseJson, Map.class);

        // Extract the "data" field from the response
        Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
        StockDTO returnedStockDTO = objectMapper.convertValue(dataMap, StockDTO.class);

        // Validate the result
        assertEquals("Updated Stock", returnedStockDTO.getName());
    }
    @Test
    void testDeleteStock() throws Exception {
        // Perform the DELETE request and capture the result
        MvcResult result = mockMvc.perform(delete("/api/v1/stock/1"))
                .andExpect(status().isNoContent())
                .andReturn();
    }



}

