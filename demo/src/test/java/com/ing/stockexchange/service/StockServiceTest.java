package com.ing.stockexchange.service;

import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.entity.Stock;
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

public class StockServiceTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStock() {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setName("Test Stock");

        Stock stock = new Stock();
        stock.setId(1L);
        stock.setName("Test Stock");

        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        StockDTO createdStock = stockService.createStock(stockDTO);

        assertEquals("Test Stock", createdStock.getName());
    }

    @Test
    public void testUpdateStock() {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setName("Updated Stock");

        Stock existingStock = new Stock();
        existingStock.setId(1L);
        existingStock.setName("Test Stock");

        Stock updatedStock = new Stock();
        updatedStock.setId(1L);
        updatedStock.setName("Updated Stock");

        when(stockRepository.findById(1L)).thenReturn(Optional.of(existingStock));
        when(stockRepository.save(any(Stock.class))).thenReturn(updatedStock);

        StockDTO updatedStockDTO = stockService.updateStock(stockDTO);

        assertEquals("Updated Stock", updatedStockDTO.getName());
    }


    @Test
    public void testDeleteStock() {
        stockService.deleteStock(1L);
        // Here you might want to verify that the repository's deleteById method was called
    }
}
