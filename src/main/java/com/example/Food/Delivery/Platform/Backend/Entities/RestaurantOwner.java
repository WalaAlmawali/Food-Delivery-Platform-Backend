package com.example.Food.Delivery.Platform.Backend.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "restaurant_owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RestaurantOwner extends BaseEntity {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Pattern(regexp = "^\\+?[0-9]{8,15}$")
    private String phone;

    private String passwordHash;
    @NotBlank
    private String businessLicenseCode;

   /* @OneToMany(
            mappedBy = "restaurantOwner",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Restaurant> restaurants;*/
}
