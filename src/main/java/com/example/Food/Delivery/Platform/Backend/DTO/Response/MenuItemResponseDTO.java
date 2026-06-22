package com.example.Food.Delivery.Platform.Backend.DTO.Response;

import com.example.Food.Delivery.Platform.Backend.Entities.MenuItem;

public class MenuItemResponseDTO {

    private Integer id;
    private String name;
    private double price;
    private boolean isVegetarian;
    private boolean isAvailable;

    public static MenuItemResponseDTO fromEntity(MenuItem m) {
        MenuItemResponseDTO dto = new MenuItemResponseDTO();
        dto.id = m.getId();
        dto.name = m.getName();
        dto.price = m.getPrice();
        dto.isVegetarian = m.getIsVegetarian();
        dto.isAvailable = m.getIsAvailable();
        return dto;
    }
}
