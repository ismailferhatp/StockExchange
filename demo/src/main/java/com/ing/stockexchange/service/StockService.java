package com.ing.stockexchange.service;

import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.entity.Stock;
import com.ing.stockexchange.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class StockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);
    @Autowired
    private StockRepository stockRepository;
    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    @Transactional
    public StockDTO createStock(StockDTO stockDTO) {
        String symbol = stockDTO.getSymbol();
        locks.putIfAbsent(symbol, new ReentrantLock());
        ReentrantLock lock = locks.get(symbol);
        lock.lock();
        try {
            Stock stock = convertToEntity(stockDTO);
            Stock savedStock = stockRepository.save(stock);
            LOGGER.info("Stock created.");
            return convertToDTO(savedStock);
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public StockDTO updateStock(StockDTO stockDTO) {
        if (stockDTO == null || stockDTO.getSymbol() == null) {
            throw new IllegalArgumentException("StockDTO and symbol must not be null");
        }
        Long stockId = stockDTO.getId();
        locks.putIfAbsent(stockId.toString(), new ReentrantLock());
        ReentrantLock lock = locks.get(stockId.toString());
        lock.lock();
        try {
            Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new RuntimeException("Stock not found"));
            stock.setCurrentPrice(stockDTO.getCurrentPrice());
            Stock updatedStock = stockRepository.save(stock);
            LOGGER.info("Stock price updated.");
            return convertToDTO(updatedStock);
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public void deleteStock(Long stockId) {
        if (stockId == null) {
            throw new IllegalArgumentException("Stock ID must not be null");
        }
        locks.putIfAbsent(stockId.toString(), new ReentrantLock());
        ReentrantLock lock = locks.get(stockId.toString());
        lock.lock();
        try {
            Stock stock = stockRepository.findById(stockId).orElse(null);
            if (stock != null) {
                stockRepository.delete(stock);
                LOGGER.info("Stock deleted.");
            } else {
                LOGGER.warn("Stock not found.");
            }
        } finally {
            lock.unlock();
            locks.remove(stockId);
        }
    }

    private Stock convertToEntity(StockDTO stockDTO) {
        Stock stock = new Stock();
        stock.setId(stockDTO.getId());
        stock.setName(stockDTO.getName());
        stock.setDescription(stockDTO.getDescription());
        stock.setCurrentPrice(stockDTO.getCurrentPrice());
        stock.setLastUpdate(stockDTO.getLastUpdate());
        return stock;
    }

    private StockDTO convertToDTO(Stock stock) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(stock.getId());
        stockDTO.setName(stock.getName());
        stockDTO.setDescription(stock.getDescription());
        stockDTO.setCurrentPrice(stock.getCurrentPrice());
        stockDTO.setLastUpdate(stock.getLastUpdate());
        return stockDTO;
    }
}

