package com.example.Food.Delivery.Platform.Backend.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "delivery_drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DeliveryDriver extends BaseEntity {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Column(unique = true)
    @Email
    @NotBlank
    private String email;
    @Pattern(regexp = "^\\+?[0-9]{8,15}$")
    private String phone;

    private String passwordHash;

    @Column(unique = true)
    private String driverCode;

    @NotBlank
    private String vehicleType;
    @NotBlank
    private String vehiclePlate;
    private Double currentLat;

    private Double currentLng;

    @Builder.Default
    private Boolean isOnline = false;

    /*@OneToMany(
            mappedBy = "deliveryDriver",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Delivery> deliveries;*/
}
