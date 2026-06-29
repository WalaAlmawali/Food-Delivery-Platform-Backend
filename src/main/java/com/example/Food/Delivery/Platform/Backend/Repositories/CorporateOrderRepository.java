package com.example.Food.Delivery.Platform.Backend.Repositories;

import com.example.Food.Delivery.Platform.Backend.Entities.CorporateOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporateOrderRepository extends JpaRepository<CorporateOrder, Integer> {
}
