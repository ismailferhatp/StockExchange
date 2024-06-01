package com.ing.stockexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class StockDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal currentPrice;
    private Instant lastUpdate;

    @JsonIgnore
    private String symbol;

    // getters and setters

    public String getSymbol() {
        if (symbol == null || symbol.isEmpty()) {
            symbol = generateUniqueSymbol();
        }
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private String generateUniqueSymbol() {
        return  "SYM" + UUID.randomUUID().toString().substring(0, 8); // Generate a unique symbol
    }
}
