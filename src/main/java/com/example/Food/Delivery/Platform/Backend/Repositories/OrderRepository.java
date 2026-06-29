package com.example.Food.Delivery.Platform.Backend.Repositories;

import com.example.Food.Delivery.Platform.Backend.Entities.Order;
import com.example.Food.Delivery.Platform.Backend.Enums.DeliveryStatus;
import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.isActive = true")
    List<Order> findByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId AND o.status = :status AND o.isActive = true")
    List<Order> findByRestaurantIdAndStatus(@Param("restaurantId") Integer restaurantId, @Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :start AND :end AND o.isActive = true")
    List<Order> findByOrderDateBetween(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.restaurant.id = :restaurantId AND o.status = :status AND o.isActive = true")
    Long countCompletedOrdersByRestaurant(
            @Param("restaurantId") Integer restaurantId,
            @Param("status") OrderStatus status
    );
    /*@Query("SELECT COUNT(o) FROM Order o WHERE o.restaurant.id = :restaurantId AND o.status = com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus.COMPLETED AND o.isActive = true")
    Long countCompletedOrdersByRestaurant(@Param("restaurantId") Integer restaurantId);*/

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = :status AND FUNCTION('DATE', o.orderDate) = :date AND o.isActive = true")
    Double sumDeliveredOrdersByDate(@Param("date") Date date, @Param("status") OrderStatus status);

    @Query("""
    SELECT COALESCE(SUM(o.totalAmount), 0)
    FROM Order o
    WHERE o.restaurant.id = :restaurantId
    AND DATE(o.orderDate) = :date
""")
    BigDecimal getRestaurantRevenue(@Param("restaurantId") Integer restaurantId,
                                    @Param("date") LocalDate date);


    @Query("""
    SELECT COUNT(o)
    FROM Order o
    WHERE o.restaurant.id = :restaurantId
""")
    long countOrdersByRestaurant(@Param("restaurantId") Integer restaurantId);

    @Query("""
    SELECT COUNT(o)
    FROM Order o
    WHERE DATE(o.orderDate) = :date
""")
    long countOrdersByDate(@Param("date") LocalDate date);

    @Query("""
    SELECT COALESCE(SUM(o.deliveryFee), 0)
    FROM Order o
    WHERE DATE(o.orderDate) = :date
""")
    BigDecimal sumDeliveryFeesByDate(@Param("date") LocalDate date);

    @Query("""
    SELECT o
    FROM Order o
    WHERE o.customer.id = :customerId
      AND (:status IS NULL OR o.status = :status)
      AND (:from IS NULL OR o.orderDate >= :from)
      AND (:to IS NULL OR o.orderDate <= :to)
    ORDER BY o.orderDate DESC
""")
    Page<Order> findCustomerOrders(
            @Param("customerId") Long customerId,
            @Param("status") DeliveryStatus status,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable
    );

    @Query("""

            SELECT COALESCE(SUM(o.totalAmount), 0)
FROM Order o
WHERE o.restaurant.id = :id
AND o.status = :status
""")
    BigDecimal totalRevenueByRestaurant(@Param("id") Integer id,
                                        @Param("status") OrderStatus status);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.restaurant.id = :id AND o.status = :status")
    long countCompletedOrders(@Param("id") Integer id,
                              @Param("status") OrderStatus status);
    }


