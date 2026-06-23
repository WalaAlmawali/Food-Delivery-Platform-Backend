package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.Restaurant;
import com.example.Food.Delivery.Platform.Backend.Entities.RestaurantOwner;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String cuisineType;

    @DecimalMin("0.0")
    private double deliveryFee;

    @NotNull
    private RestaurantOwner owner;

    public static Restaurant toEntity(RestaurantRequestDTO dto){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setCuisineType(dto.getCuisineType());
        restaurant.setDeliveryFee(dto.getDeliveryFee());
        restaurant.setRestaurantOwner(dto.getOwner());
        return restaurant;
    }
}
