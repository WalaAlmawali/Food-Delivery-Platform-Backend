package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Backend.Utils.HelperUtils;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
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

    public DeliveryDriver toEntity(){
        DeliveryDriver driver = new DeliveryDriver();
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setEmail(email);
        driver.setPhone(phone);
        driver.setDriverCode(HelperUtils.generateCode("Driver"));
        return driver;
    }
}
