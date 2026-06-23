package com.example.Food.Delivery.Platform.Backend.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "delivery_drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDriver extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String passwordHash;
    private String driverCode;
    private String vehicleType;
    private String vehiclePlate;
    private Double currentLat;

    private Double currentLng;
    private Boolean isOnline = false;

    @OneToMany(
            mappedBy = "deliveryDriver",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Delivery> deliveries;
}
