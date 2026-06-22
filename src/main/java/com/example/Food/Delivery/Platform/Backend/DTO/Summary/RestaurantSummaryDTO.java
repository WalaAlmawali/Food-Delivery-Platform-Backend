package com.example.Food.Delivery.Platform.Backend.DTO.Summary;

import com.example.Food.Delivery.Platform.Backend.Entities.Restaurant;
import lombok.Data;

@Data
public class RestaurantSummaryDTO {

    private Integer id;
    private String name;
    private String cuisineType;

    public static RestaurantSummaryDTO fromEntity(Restaurant restaurant) {
        RestaurantSummaryDTO dto = new RestaurantSummaryDTO();
        dto.id = restaurant.getId();
        dto.name = restaurant.getName();
        dto.cuisineType = restaurant.getCuisineType();
        return dto;
    }

}
