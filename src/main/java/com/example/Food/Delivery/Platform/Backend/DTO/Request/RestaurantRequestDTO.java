package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RestaurantRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String cuisineType;

    @DecimalMin("0.0")
    private double deliveryFee;

    @NotNull
    private Integer ownerId;
}
