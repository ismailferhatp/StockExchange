package com.ing.stockexchange.util;


import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static <T> Map<String, Object> createSuccessResponse(String message, T data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> createErrorResponse(String message, Object errors) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("errors", errors);
        return response;
    }
}

