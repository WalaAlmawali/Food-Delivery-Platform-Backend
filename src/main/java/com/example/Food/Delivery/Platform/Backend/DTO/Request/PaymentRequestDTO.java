package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.Order;
import com.example.Food.Delivery.Platform.Backend.Entities.Payment;
import com.example.Food.Delivery.Platform.Backend.Enums.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    @NotNull
    private Order order;

    @DecimalMin("0.0")
    private double amount;

    @Pattern(regexp = "CARD|CASH|ONLINE")
    private PaymentMethod method;

    public static Payment toEntity(PaymentRequestDTO dto){
        Payment payment = new Payment();
        payment.setOrder(dto.getOrder());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getMethod());
        return payment;
    }
}
