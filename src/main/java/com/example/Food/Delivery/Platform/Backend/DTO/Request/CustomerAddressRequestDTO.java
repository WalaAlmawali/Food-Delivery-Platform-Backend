package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
import com.example.Food.Delivery.Platform.Backend.Entities.CustomerAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerAddressRequestDTO {

    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String building;
    @NotNull
    private Customer customer;

    public static CustomerAddress  toEntity(CustomerAddressRequestDTO dto){
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomer(dto.getCustomer());
        customerAddress.setCity(dto.getCity());
        customerAddress.setBuilding(dto.getBuilding());
        customerAddress.setStreet(dto.getStreet());
        return customerAddress;
    }
}
