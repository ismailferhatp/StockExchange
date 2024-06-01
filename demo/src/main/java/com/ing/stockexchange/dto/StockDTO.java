package com.ing.stockexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
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
        String newSymbol = "SYM" + UUID.randomUUID().toString().substring(0, 8); // Generate a unique symbol
        return newSymbol;
    }
}
