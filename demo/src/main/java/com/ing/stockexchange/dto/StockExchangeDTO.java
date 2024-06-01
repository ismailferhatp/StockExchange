package com.ing.stockexchange.dto;

import lombok.Data;

import java.util.Set;

@Data
public class StockExchangeDTO {
    private Long id;
    private String name;
    private String description;
    private boolean liveInMarket;
    private Set<StockDTO> stocks;

    // getters and setters
}

