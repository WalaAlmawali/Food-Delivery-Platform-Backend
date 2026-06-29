package com.example.Food.Delivery.Platform.Backend.Repositories;

import com.example.Food.Delivery.Platform.Backend.Entities.Delivery;
import com.example.Food.Delivery.Platform.Backend.Entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.restaurant.id = :restaurantId")
    List<Review> findReviewsByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT r FROM Review r WHERE r.deliveryDriver.id = :driverId")
    List<Review> findReviewsByDriverId(@Param("driverId") Integer driverId);

    @Query("""
    SELECT COALESCE(AVG(r.rating), 0)
    FROM Review r
    WHERE r.restaurant.id = :id
""")
    double averageRatingByRestaurant(Integer id);
}
