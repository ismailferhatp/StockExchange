package com.ing.stockexchange.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal currentPrice;
    private Instant lastUpdate;

    @ManyToMany(mappedBy = "stocks")
    @JsonBackReference
    private Set<StockExchange> stockExchanges = new HashSet<>();

    // Getters and setters
}

