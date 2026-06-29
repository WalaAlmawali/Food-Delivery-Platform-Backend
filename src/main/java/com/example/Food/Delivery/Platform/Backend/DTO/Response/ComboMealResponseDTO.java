package com.example.Food.Delivery.Platform.Backend.DTO.Response;

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
public class ComboMealResponseDTO {

    private String comboName;
    private String description;
    private Double totalPrice;
    private Boolean isAvailable = true;

    private List<MenuItem> menuItems;
    private Integer restaurantId;

    public static ComboMealResponseDTO fromEntity(ComboMeal meal){
        ComboMealResponseDTO dto = new ComboMealResponseDTO();
        dto.comboName = meal.getComboName();
        dto.description = meal.getDescription();
        dto.totalPrice = meal.getTotalPrice();
        dto.menuItems = meal.getMenuItems();
        dto.restaurantId = meal.getRestaurant().getId();

        return dto;
    }
}
