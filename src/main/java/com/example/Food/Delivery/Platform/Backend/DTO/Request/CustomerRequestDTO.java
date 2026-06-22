package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerRequestDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @Pattern(regexp = "\\+?[0-9]{8,15}")
    private String phone;

}
