package com.example.Food.Delivery.Platform.Backend.DTO.Summary;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestaurantAnalyticsDTO {
    private double averageRating;
    private BigDecimal totalRevenue;
    private long totalCompletedOrders;
}
