package com.alvaro.usuario_service.exception.controller;

import com.alvaro.usuario_service.dto.ApiResponse;
import com.alvaro.usuario_service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException (ResourceNotFoundException e) {
        String message = e.getMessage();

        ApiResponse response = new ApiResponse();
        response.setMessage(message);
        response.setSuccess(true);
        response.setHttpStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
