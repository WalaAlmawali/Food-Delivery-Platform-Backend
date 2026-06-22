package com.example.Food.Delivery.Platform.Backend.DTO.Response;

import com.example.Food.Delivery.Platform.Backend.DTO.Summary.CustomerSummaryDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.Customer;

public class CustomerResponseDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public static CustomerResponseDTO fromEntity(Customer c) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.id = c.getId();
        dto.firstName = c.getFirstName();
        dto.lastName = c.getLastName();
        dto.email = c.getEmail();
        dto.phone = c.getPhone();

        return dto;
    }
}
