package com.example.Food.Delivery.Platform.Backend.DTO.Response;

import com.example.Food.Delivery.Platform.Backend.DTO.Summary.RestaurantSummaryDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.Restaurant;

public class RestaurantResponseDTO {

    private Integer id;
    private String name;
    private String cuisineType;
    private double deliveryFee;
    private boolean acceptingOrders;

    public static RestaurantResponseDTO fromEntity(Restaurant r) {
        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.id = r.getId();
        dto.name = r.getName();
        dto.cuisineType = r.getCuisineType();
        dto.deliveryFee = r.getDeliveryFee();
        dto.acceptingOrders = r.getAcceptingOrders();
        return dto;
    }
}
