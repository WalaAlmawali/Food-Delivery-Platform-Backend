package com.example.Food.Delivery.Platform.Backend.Repositories;

import com.example.Food.Delivery.Platform.Backend.Entities.Delivery;
import com.example.Food.Delivery.Platform.Backend.Enums.DeliveryStatus;
import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    @Query("SELECT d FROM Delivery d WHERE d.deliveryDriver.id = :driverId AND d.status = :status AND d.isActive = true")
    List<Delivery> findByDeliveryDriverIdAndStatus(@Param("driverId") Integer driverId, @Param("status") OrderStatus status);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryDriver.id = :driverId AND d.isActive = true")
    List<Delivery> findAllByDriverId(@Param("driverId") Integer driverId);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryDriver.id = :driverId AND d.status =:status  AND d.isActive = true")
    List<Delivery> findActiveDeliveryByDriver(@Param("driverId") Integer driverId,@Param("status") DeliveryStatus status);

    @Query("SELECT d FROM Delivery d WHERE d.status = :status AND d.isActive = true")
    List<Delivery> findAllByStatus(@Param("status") OrderStatus status);

    @Query("SELECT COUNT(d) FROM Delivery d WHERE d.deliveryDriver.id = :driverId AND d.status = :status AND d.isActive = true")
    Long countCompletedDeliveriesByDriver(@Param("driverId") Integer driverId, @Param("status") DeliveryStatus status);
}
