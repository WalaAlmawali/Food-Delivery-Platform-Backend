package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.RestaurantOwner;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RestaurantOwnerRequestDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    @NotBlank
    private String email;
    @Pattern(regexp = "\\+?[0-9]{8,15}")
    private String phone;
    @NotBlank
    private String passwordHash;
    @NotBlank
    private String businessLicenseCode;

    public static RestaurantOwner toEntity(RestaurantOwnerRequestDTO dto) {
        RestaurantOwner owner = new RestaurantOwner();

        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setEmail(dto.getEmail());
        owner.setPhone(dto.getPhone());
        owner.setPasswordHash(dto.getPasswordHash());
        owner.setBusinessLicenseCode(dto.getBusinessLicenseCode());

        return owner;

    }
}
