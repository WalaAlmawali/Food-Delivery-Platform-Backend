package com.example.Food.Delivery.Platform.Backend.DTO.Summary;

import com.example.Food.Delivery.Platform.Backend.Entities.Delivery;
import com.example.Food.Delivery.Platform.Backend.Enums.DeliveryStatus;

public class DeliverySummaryDTO {

    private Integer id;
    private DeliveryStatus status;
    private DriverSummaryDTO driver;

    public static DeliverySummaryDTO fromEntity(Delivery d){
        DeliverySummaryDTO dto = new DeliverySummaryDTO();
        dto.id = d.getId();
        dto.status = d.getStatus();
        dto.driver = DriverSummaryDTO.fromEntity(d.getDeliveryDriver());

        return dto;
    }

}
