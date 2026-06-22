package com.example.Food.Delivery.Platform.Backend.DTO.Summary;

import com.example.Food.Delivery.Platform.Backend.Entities.Order;
import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;

public class OrderSummaryDTO {

    private Integer id;
    private double totalAmount;
    private OrderStatus status;

    public static OrderSummaryDTO fromEntity(Order o){
        OrderSummaryDTO dto = new OrderSummaryDTO();
        dto.id= o.getId();
        dto.totalAmount = o.getTotalAmount();
        dto.status = o.getStatus();
        return dto;
    }
}
