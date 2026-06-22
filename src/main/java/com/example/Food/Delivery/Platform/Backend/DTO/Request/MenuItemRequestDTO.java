package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.MenuItem;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class MenuItemRequestDTO {

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

    public MenuItem toEntity(){
        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        menuItem.setCalories(calories);

        return menuItem;
    }

}
