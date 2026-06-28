package com.example.Food.Delivery.Platform.Backend.Exceptions;

public class InvalidRequestException extends RuntimeException {
  public InvalidRequestException(String message) {
    super(message);
  }
}
