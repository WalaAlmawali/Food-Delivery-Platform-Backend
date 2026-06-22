package com.example.Food.Delivery.Platform.Backend.DTO.Summary;

import com.example.Food.Delivery.Platform.Backend.Entities.MenuItem;

public class MenuItemSummaryDTO {

    private Integer id;
    private String name;
    private double price;

    public static MenuItemSummaryDTO fromEntity(MenuItem item) {
        MenuItemSummaryDTO dto = new MenuItemSummaryDTO();
        dto.id = item.getId();
        dto.name = item.getName();
        dto.price = item.getPrice();
        return dto;
    }
}
