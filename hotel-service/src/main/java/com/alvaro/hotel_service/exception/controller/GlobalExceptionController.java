package com.alvaro.hotel_service.exception.controller;

import com.alvaro.hotel_service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerResourceNotFoundException (ResourceNotFoundException e) {
        String message = e.getMessage();

        Map response = new HashMap();
        response.put("message", e.getMessage());
        response.put("success", false);
        response.put("status", HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}