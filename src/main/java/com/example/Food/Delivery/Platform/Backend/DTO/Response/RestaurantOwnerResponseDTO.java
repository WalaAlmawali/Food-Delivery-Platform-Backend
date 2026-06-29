package com.example.Food.Delivery.Platform.Backend.DTO.Response;

import com.example.Food.Delivery.Platform.Backend.Entities.RestaurantOwner;
import lombok.Data;

@Data
public class RestaurantOwnerResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public static RestaurantOwnerResponseDTO fromEntity(RestaurantOwner owner){

        RestaurantOwnerResponseDTO dto = new RestaurantOwnerResponseDTO();
        dto.firstName = owner.getFirstName();
        dto.lastName = owner.getLastName() ;
        dto.email = owner.getEmail();
        dto.phone = owner.getPhone();

        return dto;
    }
}
