package com.ing.stockexchange.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.stockexchange.controller.StockExchangeController;
import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.dto.StockExchangeDTO;
import com.ing.stockexchange.service.StockExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

class StockExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StockExchangeService stockExchangeService;

    @InjectMocks
    private StockExchangeController stockExchangeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(stockExchangeController).build();
    }

    @Test
    void testGetStockExchange() throws Exception {
        StockExchangeDTO stockExchangeDTO = new StockExchangeDTO();
        stockExchangeDTO.setName("Test Exchange");

        when(stockExchangeService.findByName("Test Exchange")).thenReturn(stockExchangeDTO);

        MvcResult result = mockMvc.perform(get("/api/v1/stock-exchange/Test Exchange"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(responseJson, Map.class);

        Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
        StockExchangeDTO returnedStockExchangeDTO = objectMapper.convertValue(dataMap, StockExchangeDTO.class);

        assertEquals("Test Exchange", returnedStockExchangeDTO.getName());
    }

    @Test
    void testAddStockToExchange() throws Exception {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setName("Test Stock");

        // Create a StockExchangeDTO object
        StockExchangeDTO stockExchangeDTO = new StockExchangeDTO();
        stockExchangeDTO.setName("Test Exchange");

        // Mock the service call
        when(stockExchangeService.addStockToExchange(ArgumentMatchers.eq("Test Exchange"), any(StockDTO.class)))
                .thenReturn(stockExchangeDTO);

        // Convert the stockDTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String stockDTOJson = objectMapper.writeValueAsString(stockDTO);

        // Perform the POST request and capture the result
        MvcResult result = mockMvc.perform(post("/api/v1/stock-exchange/add-stock/Test Exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockDTOJson))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response JSON
        String responseJson = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseJson, Map.class);

        // Extract the "data" field from the response
        Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
        StockExchangeDTO returnedStockExchangeDTO = objectMapper.convertValue(dataMap, StockExchangeDTO.class);

        // Validate the result
        assertEquals("Test Exchange", returnedStockExchangeDTO.getName());
    }

    @Test
    void testRemoveStockFromExchange() throws Exception {
        // Create a StockDTO object
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setName("Test Stock");

        // Create a StockExchangeDTO object
        StockExchangeDTO stockExchangeDTO = new StockExchangeDTO();
        stockExchangeDTO.setName("Test Exchange");

        // Mock the service call
        when(stockExchangeService.removeStockFromExchange(eq("Test Exchange"), any(StockDTO.class)))
                .thenReturn(stockExchangeDTO);

        // Convert the stockDTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String stockDTOJson = objectMapper.writeValueAsString(stockDTO);

        // Perform the DELETE request and capture the result
        MvcResult result = mockMvc.perform(delete("/api/v1/stock-exchange/remove-stock/Test Exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockDTOJson))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response JSON
        String responseJson = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseJson, Map.class);

        // Extract the "data" field from the response
        Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
        StockExchangeDTO returnedStockExchangeDTO = objectMapper.convertValue(dataMap, StockExchangeDTO.class);

        // Validate the result
        assertEquals("Test Exchange", returnedStockExchangeDTO.getName());
    }

}
