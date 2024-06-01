package com.ing.stockexchange.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

@Entity
@Data
public class StockExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private boolean liveInMarket;

    @ManyToMany
    @JoinTable(
            name = "stock_exchange_stock",
            joinColumns = @JoinColumn(name = "stock_exchange_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id")
    )

    @JsonManagedReference
    private Set<Stock> stocks = new HashSet<>();

    // Getters and setters

    public void addStock(Stock stock) {
        this.stocks.add(stock);
        updateLiveInMarketStatus();
    }

    public void removeStock(Stock stock) {
        this.stocks.remove(stock);
        updateLiveInMarketStatus();
    }

    private void updateLiveInMarketStatus() {
        this.liveInMarket = this.stocks.size() >= 5;
    }

    // Getters and setters
}

