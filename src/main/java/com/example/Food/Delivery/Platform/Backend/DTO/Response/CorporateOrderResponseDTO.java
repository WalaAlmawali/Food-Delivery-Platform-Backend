package com.example.Food.Delivery.Platform.Backend.DTO.Response;

import com.example.Food.Delivery.Platform.Backend.Entities.CorporateOrder;
import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CorporateOrderResponseDTO {

    private String companyName;
    private OrderStatus status;
    private Double totalAmount;

    public static CorporateOrderResponseDTO fromEntity(CorporateOrder order) {
        CorporateOrderResponseDTO dto = new CorporateOrderResponseDTO();

        dto.setCompanyName(order.getCompanyName());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        return dto;
    }

    public static List<CorporateOrderResponseDTO> fromEntity(List<CorporateOrder> orders) {

        List<CorporateOrderResponseDTO> dtos = new ArrayList<>();
        for (CorporateOrder entity : orders) {
            dtos.add(fromEntity(entity));
        }
        return dtos;
    }
}
