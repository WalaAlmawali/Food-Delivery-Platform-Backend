package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import jakarta.validation.constraints.*;

public class MenuItemRequestDTO {

    @NotBlank
    private String name;

    @DecimalMin("0.0")
    private double price;

    @NotNull
    private Integer restaurantId;

    private boolean isVegetarian;
    private boolean isAvailable;
}
