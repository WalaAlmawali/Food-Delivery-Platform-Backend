package com.example.Food.Delivery.Platform.Backend.DTO.Summary;

import com.example.Food.Delivery.Platform.Backend.Entities.DeliveryDriver;

public class DriverSummaryDTO {

    private Integer id;
    private String name;

    public static DriverSummaryDTO fromEntity(DeliveryDriver deliveryDriver){
        DriverSummaryDTO dto = new DriverSummaryDTO();
        dto.id=deliveryDriver.getId();
        dto.name=deliveryDriver.getFirstName();
        return dto;
    }
}
