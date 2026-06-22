package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.MenuItem;
import com.example.Food.Delivery.Platform.Backend.Entities.Order;
import com.example.Food.Delivery.Platform.Backend.Entities.OrderItem;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderItemDTO {
    @NotNull
    private MenuItem Item;

    @PositiveOrZero
    private double price;

    @Min(1)
    private int quantity;

    public OrderItem toEntity(){
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(Item);
        orderItem.setUnitPrice(price);
        orderItem.setQuantity(quantity);

        return orderItem;
    }
}
