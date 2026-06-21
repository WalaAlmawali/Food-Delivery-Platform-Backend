package com.example.Food.Delivery.Platform.Backend.Entities;

import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "corporate_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CorporateOrder extends BaseEntity{
    @Column(unique = true)
    private String corporateCode;
    @NotBlank
    private String companyName;
    @NotBlank
    private String costCenter;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @PositiveOrZero
    @DecimalMin("0.0")
    private Double totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Restaurant restaurant;

    @OneToMany(
            mappedBy = "corporateOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CorporateOrderItem> corporateOrderItems;

}
