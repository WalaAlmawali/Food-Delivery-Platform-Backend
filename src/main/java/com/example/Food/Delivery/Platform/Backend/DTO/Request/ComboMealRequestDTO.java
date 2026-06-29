package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.ComboMeal;
import com.example.Food.Delivery.Platform.Backend.Entities.MenuItem;
import com.example.Food.Delivery.Platform.Backend.Entities.Restaurant;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class ComboMealRequestDTO {
    @NotBlank
    private String comboName;
    @NotBlank
    private String description;
    @PositiveOrZero
    @DecimalMin("0.0")
    private Double totalPrice;
    private Boolean isAvailable = true;
    @NotNull
    private List<MenuItem> menuItems;
    @NotBlank
    private Restaurant restaurant;


    public static ComboMeal toEntity(ComboMealRequestDTO dto){
        ComboMeal meal = new ComboMeal();
        meal.setComboName(dto.getComboName());
        meal.setDescription(dto.getDescription());
        meal.setTotalPrice(dto.getTotalPrice());
        meal.setMenuItems(dto.getMenuItems());
        meal.setRestaurant(dto.getRestaurant());

        return meal;
    }
}
