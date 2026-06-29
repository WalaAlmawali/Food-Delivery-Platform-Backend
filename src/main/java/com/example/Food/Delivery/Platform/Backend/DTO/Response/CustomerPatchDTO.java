package com.example.Food.Delivery.Platform.Backend.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerPatchDTO {

    private String phone;
    private String address;
    private String firstName;
    private String lastName;
    private String email;

}
