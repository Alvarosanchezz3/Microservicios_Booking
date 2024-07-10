package com.alvaro.hotel_service.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super("No encontrado");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
