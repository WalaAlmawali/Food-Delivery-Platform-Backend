package com.example.Food.Delivery.Platform.Backend.Repositories;

import com.example.Food.Delivery.Platform.Backend.Entities.ComboMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComboMealRepository extends JpaRepository<ComboMeal, Integer> {

    @Query("SELECT c FROM ComboMeal c WHERE c.restaurant.id = :restaurantId AND c.isActive = true")
    List<ComboMeal> findAllByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT DISTINCT c FROM ComboMeal c JOIN c.menuItems i WHERE i.id = :itemId AND c.isActive = true AND i.isActive = true")
    List<ComboMeal> findByMenuItemId(@Param("itemId") Integer itemId);
}
