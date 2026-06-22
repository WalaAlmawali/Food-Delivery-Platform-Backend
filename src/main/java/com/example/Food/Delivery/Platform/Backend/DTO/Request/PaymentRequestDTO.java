package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Enums.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotNull
    private Integer orderId;

    @DecimalMin("0.0")
    private double amount;

    @Pattern(regexp = "CARD|CASH|ONLINE")
    private PaymentMethod method;
}
