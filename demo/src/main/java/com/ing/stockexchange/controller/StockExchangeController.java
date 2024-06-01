package com.ing.stockexchange.controller;

import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.dto.StockExchangeDTO;
import com.ing.stockexchange.service.StockExchangeService;
import com.ing.stockexchange.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/stock-exchange")
public class StockExchangeController {

    @Autowired
    private StockExchangeService stockExchangeService;

    @GetMapping("/{name}")
    public ResponseEntity<Map<String, Object>> getStockExchange(@PathVariable String name) {
        StockExchangeDTO stockExchangeDTO = stockExchangeService.findByName(name);
        Map<String, Object> response = ResponseUtil.createSuccessResponse("Stock exchange retrieved successfully.", stockExchangeDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-stock/{exchangeName}")
    public ResponseEntity<Map<String, Object>> addStockToExchange(@PathVariable String exchangeName, @RequestBody StockDTO stockDTO) {
        StockExchangeDTO updatedStockExchangeDTO = stockExchangeService.addStockToExchange(exchangeName, stockDTO);
        Map<String, Object> response = ResponseUtil.createSuccessResponse("Stock added to exchange successfully.", updatedStockExchangeDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove-stock/{exchangeName}")
    public ResponseEntity<Map<String, Object>> removeStockFromExchange(@PathVariable String exchangeName, @RequestBody StockDTO stockDTO) {
        StockExchangeDTO updatedStockExchangeDTO = stockExchangeService.removeStockFromExchange(exchangeName, stockDTO);
        Map<String, Object> response = ResponseUtil.createSuccessResponse("Stock removed from exchange successfully.", updatedStockExchangeDTO);
        return ResponseEntity.ok(response);
    }
}
