package com.example.Food.Delivery.Platform.Backend.Exceptions;

public class InvalidOrderStateException extends RuntimeException{
    public InvalidOrderStateException(String message) {
        super(message);
    }
}
