package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.MenuItem;
import com.example.Food.Delivery.Platform.Backend.Entities.OrderItem;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequestDTO {
    @NotNull
    private MenuItem Item;

    @PositiveOrZero
    private double price;

    @Min(1)
    private int quantity;

    public static OrderItem toEntity(OrderItemRequestDTO dto){

        OrderItem orderItem = new OrderItem();

        orderItem.setMenuItem(dto.getItem());
        orderItem.setUnitPrice(dto.getPrice());
        orderItem.setQuantity(dto.getQuantity());

        return orderItem;
    }
}
