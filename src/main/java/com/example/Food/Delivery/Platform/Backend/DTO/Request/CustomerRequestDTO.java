package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
import com.example.Food.Delivery.Platform.Backend.Utils.HelperUtils;
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
    @NotBlank
    private String passwordHash;


    public Customer toEntity(){
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setCustomerCode(HelperUtils.generateCode("CUST"));
        customer.setPasswordHash(passwordHash);
        return customer;
    }

}
