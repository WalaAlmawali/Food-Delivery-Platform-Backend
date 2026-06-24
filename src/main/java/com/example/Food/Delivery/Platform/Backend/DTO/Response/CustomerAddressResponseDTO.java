package com.example.Food.Delivery.Platform.Backend.DTO.Response;

import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
import com.example.Food.Delivery.Platform.Backend.Entities.CustomerAddress;
import lombok.Data;

@Data
public class CustomerAddressResponseDTO {

    private String street;
    private String city;
    private String building;
    private Customer customer;


    public static CustomerAddressResponseDTO fromEntity(CustomerAddress address){
        CustomerAddressResponseDTO dto = new CustomerAddressResponseDTO();
        dto.street = address.getStreet();
        dto.city = address.getCity();
        dto.building = address.getBuilding();
        dto.customer = address.getCustomer();
        return dto;
    }
}
