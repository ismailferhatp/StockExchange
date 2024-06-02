package com.ing.stockexchange.exception;

public class StockExchangeNotFoundException extends RuntimeException {
    public StockExchangeNotFoundException(String message) {
        super(message);
    }
}

