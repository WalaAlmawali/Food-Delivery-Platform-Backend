package com.example.Food.Delivery.Platform.Backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "corporate_order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CorporateOrderItem extends BaseEntity{
    private Integer quantity;

    private Double unitPrice;

    private Double totalPrice;

    private String specialInstructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_order_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CorporateOrder corporateOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MenuItem menuItem;

}
