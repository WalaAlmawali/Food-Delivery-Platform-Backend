package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Backend.Utils.HelperUtils;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverRequestDTO {

    @NotNull
    private Integer driverId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{8,15}$")
    private String phone;

    public static DeliveryDriver toEntity(DriverRequestDTO dto){
        DeliveryDriver driver = new DeliveryDriver();
        driver.setFirstName(dto.getFirstName());
        driver.setLastName(dto.getLastName());
        driver.setEmail(dto.getEmail());
        driver.setPhone(dto.getPhone());
        driver.setDriverCode(HelperUtils.generateCode("Driver"));
        return driver;
    }
}
