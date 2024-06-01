package com.ing.stockexchange.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class StockControllerTest {

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
    public void testCreateStock() throws Exception {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setName("Test Stock");


        when(stockService.createStock(stockDTO)).thenReturn(stockDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String stockDTOJson = objectMapper.writeValueAsString(stockDTO);

        MvcResult result = mockMvc.perform(post("/api/v1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockDTOJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseJson, Map.class);

        Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
        StockDTO returnedStockDTO = objectMapper.convertValue(dataMap, StockDTO.class);

        assertEquals("Test Stock", returnedStockDTO.getName());
    }

    @Test
    public void testUpdateStock() throws Exception {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setName("Updated Stock");


        when(stockService.updateStock(stockDTO)).thenReturn(stockDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String stockDTOJson = objectMapper.writeValueAsString(stockDTO);

        MvcResult result = mockMvc.perform(put("/api/v1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockDTOJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseJson, Map.class);

        Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
        StockDTO returnedStockDTO = objectMapper.convertValue(dataMap, StockDTO.class);

        assertEquals("Updated Stock", returnedStockDTO.getName());
    }

    @Test
    public void testDeleteStock() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/v1/stock/1"))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}

