package com.ing.stockexchange.service;

import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.dto.StockExchangeDTO;
import com.ing.stockexchange.entity.Stock;
import com.ing.stockexchange.entity.StockExchange;
import com.ing.stockexchange.exception.ResourceNotFoundException;
import com.ing.stockexchange.repository.StockExchangeRepository;
import com.ing.stockexchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class StockExchangeService {
    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Autowired
    private StockRepository stockRepository;

    private final ConcurrentHashMap<String, ReentrantLock> exchangeLocks = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ReentrantLock> stockLocks = new ConcurrentHashMap<>();

    @Transactional
    public StockExchangeDTO addStockToExchange(String exchangeName, StockDTO stockDTO) {
        exchangeLocks.putIfAbsent(exchangeName, new ReentrantLock());
        stockLocks.putIfAbsent(stockDTO.getSymbol(), new ReentrantLock());

        ReentrantLock exchangeLock = exchangeLocks.get(exchangeName);
        ReentrantLock stockLock = stockLocks.get(stockDTO.getSymbol());

        exchangeLock.lock();
        stockLock.lock();
        try {
            StockExchange stockExchange = stockExchangeRepository.findByName(exchangeName);

            if (stockExchange == null) {
                throw new RuntimeException("Stock Exchange not found");
            }

            Stock stock = stockRepository.findByName(stockDTO.getName());
            if (stock == null) {
                stock = new Stock();
                stock.setName(stockDTO.getName());
                stock.setDescription(stockDTO.getDescription());
                stock.setCurrentPrice(stockDTO.getCurrentPrice());
                stock.setLastUpdate(stockDTO.getLastUpdate());
                stock = stockRepository.save(stock);
            }

            stockExchange.addStock(stock);

            // Assuming stockExchange is an instance of StockExchange and stocks is a collection within it
            stockExchange.setLiveInMarket(stockExchange.getStocks().size() >= 5);
            stockExchange = stockExchangeRepository.save(stockExchange);

            return convertToDto(stockExchange);
        } finally {
            stockLock.unlock();
            exchangeLock.unlock();
        }
    }

    @Transactional
    public StockExchangeDTO removeStockFromExchange(String exchangeName, StockDTO stockDTO) {
        exchangeLocks.putIfAbsent(exchangeName, new ReentrantLock());
        stockLocks.putIfAbsent(stockDTO.getSymbol(), new ReentrantLock());

        ReentrantLock exchangeLock = exchangeLocks.get(exchangeName);
        ReentrantLock stockLock = stockLocks.get(stockDTO.getSymbol());

        exchangeLock.lock();
        stockLock.lock();
        try {
            StockExchange stockExchange = stockExchangeRepository.findByName(exchangeName);
            Stock stock = stockRepository.findByName(stockDTO.getName());

            if (stock != null) {
                stockExchange.removeStock(stock);
                stockExchange = stockExchangeRepository.save(stockExchange);

                return convertToDto(stockExchange);
            } else {
                throw new RuntimeException("Stock not found");
            }
        } finally {
            stockLock.unlock();
            exchangeLock.unlock();
        }
    }

    private StockExchangeDTO convertToDto(StockExchange stockExchange) {
        StockExchangeDTO stockExchangeDTO = new StockExchangeDTO();
        stockExchangeDTO.setId(stockExchange.getId());
        stockExchangeDTO.setName(stockExchange.getName());
        stockExchangeDTO.setDescription(stockExchange.getDescription());
        stockExchangeDTO.setLiveInMarket(stockExchange.isLiveInMarket());

        Set<StockDTO> stockDTOs = stockExchange.getStocks().stream().map(stock -> {
            StockDTO stockDTO = new StockDTO();
            stockDTO.setId(stock.getId());
            stockDTO.setName(stock.getName());
            stockDTO.setDescription(stock.getDescription());
            stockDTO.setCurrentPrice(stock.getCurrentPrice());
            stockDTO.setLastUpdate(stock.getLastUpdate());
            return stockDTO;
        }).collect(Collectors.toSet());

        stockExchangeDTO.setStocks(stockDTOs);

        return stockExchangeDTO;
    }

    public StockExchangeDTO findByName(String name) {
        StockExchange stockExchange = stockExchangeRepository.findByName(name);

        if (stockExchange == null)
            throw new ResourceNotFoundException("Stock Exchange not found");

        return convertToDto(stockExchange);
    }
}

