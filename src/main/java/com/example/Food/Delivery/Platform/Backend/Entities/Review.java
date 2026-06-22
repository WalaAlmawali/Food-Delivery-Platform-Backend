package com.example.Food.Delivery.Platform.Backend.Entities;

import com.example.Food.Delivery.Platform.Backend.Enums.ReviewTargetType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Review extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private ReviewTargetType targetType;


    private Integer rating;


    @Column(length = 1000)
    private String comment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private DeliveryDriver deliveryDriver;

}
