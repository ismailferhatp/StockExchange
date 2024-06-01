package com.ing.stockexchange.service;

import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.dto.StockExchangeDTO;
import com.ing.stockexchange.entity.Stock;
import com.ing.stockexchange.entity.StockExchange;
import com.ing.stockexchange.repository.StockExchangeRepository;
import com.ing.stockexchange.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StockExchangeServiceTest {

    @InjectMocks
    private StockExchangeService stockExchangeService;

    @Mock
    private StockExchangeRepository stockExchangeRepository;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAddStockToExchange() {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setName("Test Stock");

        Stock stock = new Stock();
        stock.setId(1L);
        stock.setName("Test Stock");

        StockExchange stockExchange = new StockExchange();
        stockExchange.setId(1L);
        stockExchange.setName("Test Exchange");


        when(stockRepository.findByName("Test Stock")).thenReturn(stock);
        when(stockExchangeRepository.findByName("Test Exchange")).thenReturn(stockExchange);
        when(stockExchangeRepository.save(any(StockExchange.class))).thenReturn(stockExchange);

        StockExchangeDTO result = stockExchangeService.addStockToExchange("Test Exchange", stockDTO);

        assertEquals("Test Exchange", result.getName());
    }


    @Test
    void testRemoveStockFromExchange() {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setName("Test Stock");

        Stock stock = new Stock();
        stock.setId(1L);
        stock.setName("Test Stock");

        StockExchange stockExchange = new StockExchange();
        stockExchange.setId(1L);
        stockExchange.setName("Test Exchange");

        when(stockRepository.findByName("Test Stock")).thenReturn(stock);
        when(stockExchangeRepository.findByName("Test Exchange")).thenReturn(stockExchange);
        when(stockExchangeRepository.save(any(StockExchange.class))).thenReturn(stockExchange);

        StockExchangeDTO result = stockExchangeService.removeStockFromExchange("Test Exchange", stockDTO);

        assertEquals("Test Exchange", result.getName());
    }
}
