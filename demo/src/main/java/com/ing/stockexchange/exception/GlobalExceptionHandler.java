package com.ing.stockexchange.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;


import java.time.Instant;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String DEFAULT_ERROR_CODE = "0";

    // Handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        LOGGER.error("Resource not found: {}", ex.getMessage(), ex);
        return buildResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleStockNotFoundException(StockNotFoundException ex, WebRequest request) {
        LOGGER.error("Stock not found for: {}", ex.getMessage(), ex);
        return buildResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockExchangeNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleStockExchangeNotFoundException(StockExchangeNotFoundException ex, WebRequest request) {
        LOGGER.error("Stock exchange not found for: {}", ex.getMessage(), ex);
        return buildResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UniqueStockNameException.class)
    public ResponseEntity<ErrorDetails> handleUniqueStockNameException(UniqueStockNameException ex, WebRequest request) {
        LOGGER.error("Stock name must be unique: {}", ex.getMessage(), ex);
        return buildResponseEntity(ex, request, HttpStatus.BAD_REQUEST);
    }

    // Handle global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        LOGGER.error("Internal server error: {}", ex.getMessage(), ex);
        return buildResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to build ErrorDetails
    private ErrorDetails buildErrorDetails(Exception ex, WebRequest request, String errorCode) {
        return new ErrorDetails(Date.from(Instant.now()), ex.getMessage(), request.getDescription(false), errorCode);
    }

    // Helper method to build ResponseEntity<ErrorDetails>
    private ResponseEntity<ErrorDetails> buildResponseEntity(Exception ex, WebRequest request, HttpStatus status) {
        ErrorDetails errorDetails = buildErrorDetails(ex, request, DEFAULT_ERROR_CODE);
        return new ResponseEntity<>(errorDetails, status);
    }
}

