package com.example.Food.Delivery.Platform.Backend.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderItem extends BaseEntity {
    @PositiveOrZero
    private Integer quantity;

    @PositiveOrZero
    @DecimalMin("0.0")
    private Double unitPrice;

    @PositiveOrZero
    @DecimalMin("0.0")
    private Double totalPrice;

    @NotBlank
    private String specialInstructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MenuItem menuItem;

}
