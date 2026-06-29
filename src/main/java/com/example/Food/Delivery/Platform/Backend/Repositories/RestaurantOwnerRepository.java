package com.example.Food.Delivery.Platform.Backend.Repositories;

import com.example.Food.Delivery.Platform.Backend.Entities.Restaurant;
import com.example.Food.Delivery.Platform.Backend.Entities.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Integer> {
}
