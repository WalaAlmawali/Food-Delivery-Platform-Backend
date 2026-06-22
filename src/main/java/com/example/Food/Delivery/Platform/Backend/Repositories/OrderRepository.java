package com.example.Food.Delivery.Platform.Backend.Repositories;

import com.example.Food.Delivery.Platform.Backend.Entities.Order;
import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.isActive = true")
    List<Order> findByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId AND o.status = :status AND o.isActive = true")
    List<Order> findByRestaurantIdAndStatus(@Param("restaurantId") Integer restaurantId, @Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :start AND :end AND o.isActive = true")
    List<Order> findByOrderDateBetween(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.restaurant.id = :restaurantId AND o.status = OrderStatus.COMPLETED AND o.isActive = true")
    Long countCompletedOrdersByRestaurant(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = OrderStatus.DELIVERED AND FUNCTION('DATE', o.orderDate) = :date AND o.isActive = true")
    Double sumDeliveredOrdersByDate(@Param("date") Date date);
}
