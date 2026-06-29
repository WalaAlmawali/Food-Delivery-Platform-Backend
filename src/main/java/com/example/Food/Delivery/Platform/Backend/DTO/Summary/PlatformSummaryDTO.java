package com.example.Food.Delivery.Platform.Backend.DTO.Summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformSummaryDTO {

    private long totalOrders;
    private BigDecimal totalDeliveryFees;

}
