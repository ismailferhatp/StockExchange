package com.ing.stockexchange.controller;

import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.service.StockService;
import com.ing.stockexchange.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createStock(@RequestBody StockDTO stockDTO) {
        StockDTO savedStockDTO = stockService.createStock(stockDTO);
        Map<String, Object> response = ResponseUtil.createSuccessResponse("Stock created successfully.", savedStockDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateStock(@RequestBody StockDTO stockDTO) {
        StockDTO updatedStockDTO = stockService.updateStock(stockDTO);
        Map<String, Object> response = ResponseUtil.createSuccessResponse("Stock updated successfully.", updatedStockDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        Map<String, Object> response = ResponseUtil.createSuccessResponse("Stock deleted successfully.", null);
        return ResponseEntity.noContent().build();
    }


}

