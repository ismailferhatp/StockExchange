package com.ing.stockexchange.exception;

public class UniqueStockNameException extends RuntimeException {
    public UniqueStockNameException(String message) {
        super(message);
    }
}

