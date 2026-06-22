package com.example.Food.Delivery.Platform.Backend.DTO.Summary;

import com.example.Food.Delivery.Platform.Backend.Entities.Payment;
import com.example.Food.Delivery.Platform.Backend.Enums.PaymentMethod;
import com.example.Food.Delivery.Platform.Backend.Enums.PaymentStatus;

public class PaymentSummaryDTO {

    private Integer id;
    private double amount;
    private PaymentMethod method;
    private PaymentStatus status;

    public static PaymentSummaryDTO fromEntity(Payment p) {
        PaymentSummaryDTO dto = new PaymentSummaryDTO();
        dto.id = p.getId();
        dto.amount = p.getAmount();
        dto.method = p.getPaymentMethod();
        dto.status = p.getStatus();
        return dto;
    }
}
