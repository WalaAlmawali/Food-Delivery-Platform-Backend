package com.example.Food.Delivery.Platform.Backend.DTO.Response;

import com.example.Food.Delivery.Platform.Backend.Entities.Payment;
import com.example.Food.Delivery.Platform.Backend.Enums.PaymentMethod;

public class PaymentResponseDTO {

    private Integer id;
    private double amount;
    private PaymentMethod method;

    public static PaymentResponseDTO fromEntity(Payment p) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.id = p.getId();
        dto.amount = p.getAmount();
        dto.method = p.getPaymentMethod();
        return dto;
    }
}
