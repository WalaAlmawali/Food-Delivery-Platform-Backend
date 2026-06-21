package com.example.Food.Delivery.Platform.Backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MenuItem extends BaseEntity {
    private String name;

    @Column(length = 1000)
    private String description;

    private Double price;

    @Builder.Default
    private Boolean isAvailable = true;

    @Builder.Default
    private Boolean isVegetarian = false;

    private Integer calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Restaurant restaurant;
   /* @OneToMany(
            mappedBy = "menuItem",
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderItem> orderItems;
*/
    @ManyToMany(mappedBy = "menuItems")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ComboMeal> comboMeals;
}
