package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.MenuItem;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MenuItemRequestDTO {

    @NotBlank
    private String name;
    @Column(length = 1000)
    private String description;
    @PositiveOrZero
    @DecimalMin("0.0")
    private Double price;

    @Builder.Default
    private Boolean isAvailable = true;

    @Builder.Default
    private Boolean isVegetarian = false;

    @PositiveOrZero
    private Integer calories;

    public static MenuItem toEntity(MenuItemRequestDTO dto){
        MenuItem menuItem = new MenuItem();
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCalories(dto.getCalories());

        return menuItem;
    }

}
