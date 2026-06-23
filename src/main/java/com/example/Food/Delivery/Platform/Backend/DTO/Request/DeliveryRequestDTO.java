package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.Delivery;
import com.example.Food.Delivery.Platform.Backend.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Backend.Entities.Order;
import com.example.Food.Delivery.Platform.Backend.Enums.DeliveryStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDTO {
    @NotNull
    private DeliveryDriver driver;

    @NotNull
    private Order order;

    @Pattern(regexp = "ASSIGNED|PICKED_UP|ON_THE_WAY|DELIVERED|FAILED")
    private DeliveryStatus status;

    public static Delivery toEntity(DeliveryRequestDTO dto){
        Delivery delivery = new Delivery();
        delivery.setDeliveryDriver(dto.getDriver());
        delivery.setOrder(dto.getOrder());
        delivery.setStatus(dto.getStatus());

        return delivery;
    }
}
