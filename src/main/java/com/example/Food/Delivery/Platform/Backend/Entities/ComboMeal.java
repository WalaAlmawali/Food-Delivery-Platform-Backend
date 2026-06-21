package com.example.Food.Delivery.Platform.Backend.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "combo_meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ComboMeal extends BaseEntity{
    @NotBlank
    private String comboName;

    @Column(length = 1000)
    private String description;
    @PositiveOrZero
    @DecimalMin("0.0")
    private Double totalPrice;

    @Builder.Default
    private Boolean isAvailable = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(
            name = "combo_meal_items",
            joinColumns = @JoinColumn(name = "combo_meal_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MenuItem> menuItems;
}
