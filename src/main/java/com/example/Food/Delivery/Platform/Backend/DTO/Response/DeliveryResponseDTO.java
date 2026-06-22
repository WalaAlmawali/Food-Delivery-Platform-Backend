package com.example.Food.Delivery.Platform.Backend.DTO.Response;

import com.example.Food.Delivery.Platform.Backend.DTO.Summary.OrderSummaryDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.Delivery;
import com.example.Food.Delivery.Platform.Backend.Enums.DeliveryStatus;

public class DeliveryResponseDTO {

    private Integer id;
    private DeliveryStatus status;

    private OrderSummaryDTO order;

    public static DeliveryResponseDTO fromEntity(Delivery d) {
        DeliveryResponseDTO dto = new DeliveryResponseDTO();
        dto.id = d.getId();
        dto.status = d.getStatus();

        dto.order = OrderSummaryDTO.fromEntity(d.getOrder());
        //dto.driver = DriverSummaryDTO.fromEntity(d.getDriver());

        return dto;
    }
}
