package com.example.Food.Delivery.Platform.Backend.Repositories;

import com.example.Food.Delivery.Platform.Backend.Entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = :id AND m.isActive = true")
    List<MenuItem> findByRestaurantId(@Param("id") Integer id);

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = :id AND m.isAvailable = true AND m.isActive = true")
    List<MenuItem> findByRestaurantIdAndIsAvailableTrue(@Param("id") Integer id);

    @Query("SELECT m FROM MenuItem m WHERE m.isVegetarian = true AND m.isActive = true")
    List<MenuItem> findByIsVegetarianTrue();

    @Query("SELECT m FROM MenuItem m WHERE m.price BETWEEN :min AND :max AND m.isActive = true")
    List<MenuItem> findByPriceBetween(@Param("min") double min, @Param("max") double max);
    @Query("""
    SELECT m
    FROM MenuItem m
    JOIN m.orderItems oi
    WHERE m.restaurant.id = :id
    GROUP BY m
    ORDER BY SUM(oi.quantity) DESC
""")
    List<MenuItem> findTopSellingItems(Long id);

    @Query("""
    SELECT m
    FROM MenuItem m
    WHERE (:keyword IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
      AND (:minCalories IS NULL OR m.calories >= :minCalories)
      AND (:maxCalories IS NULL OR m.calories <= :maxCalories)
""")
    List<MenuItem> searchMenuItems(
            String keyword,
            Integer minCalories,
            Integer maxCalories
    );
}
