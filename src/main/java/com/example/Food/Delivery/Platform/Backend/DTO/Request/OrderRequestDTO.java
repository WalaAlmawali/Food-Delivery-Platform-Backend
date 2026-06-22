package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
@Data
public class OrderRequestDTO {
    @NotNull
    private Integer customerId;

    @NotNull
    private Integer restaurantId;

    @NotEmpty
    private List<@Valid OrderItemDTO> items;

    @Pattern(regexp = "PENDING|PREPARING|READY|OUT_FOR_DELIVERY|DELIVERED|CANCELLED")
    private OrderStatus status;
}
