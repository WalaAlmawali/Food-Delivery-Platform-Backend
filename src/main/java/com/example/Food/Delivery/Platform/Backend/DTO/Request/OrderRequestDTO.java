package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
import com.example.Food.Delivery.Platform.Backend.Entities.Order;
import com.example.Food.Delivery.Platform.Backend.Entities.Restaurant;
import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    @NotNull
    private Customer customer;

    @NotNull
    private Restaurant restaurant;

    @NotEmpty
    private List<@Valid OrderItemRequestDTO> items;

    @Pattern(regexp = "PENDING|PREPARING|READY|OUT_FOR_DELIVERY|DELIVERED|CANCELLED")
    private OrderStatus status;

    public static Order toEntity(OrderRequestDTO dto) {
        Order order = new Order();
        order.setCustomer(dto.getCustomer());
        order.setRestaurant(dto.getRestaurant());
        order.setStatus(dto.getStatus());
        return order;
    }
}
