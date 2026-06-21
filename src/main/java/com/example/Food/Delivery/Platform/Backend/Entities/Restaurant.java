package com.example.Food.Delivery.Platform.Backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Restaurant extends BaseEntity{
    private String name;

    @Column(length = 1000)
    private String description;

    private String cuisineType;

    private String openingTime;

    private String closingTime;

    private Double minOrderAmount;

    private Double deliveryFee;

    @Builder.Default
    private Boolean acceptingOrders = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RestaurantOwner restaurantOwner;

    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MenuItem> menuItems;

    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ComboMeal> comboMeals;
}
