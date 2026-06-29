package com.example.Food.Delivery.Platform.Backend.Repositories;

import com.example.Food.Delivery.Platform.Backend.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Backend.Enums.DeliveryStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Driver;
import java.util.List;
import java.util.Optional;

public interface DeliveryDriverRepository extends JpaRepository<DeliveryDriver, Integer> {

    @Query("""
SELECT d
FROM DeliveryDriver d
WHERE d.isOnline = true
AND NOT EXISTS (
    SELECT del
    FROM Delivery del
    WHERE del.deliveryDriver = d
      AND del.status = :status
)
ORDER BY d.id ASC
""")
    Optional<DeliveryDriver> findFirstAvailableDriver(
            @Param("status") DeliveryStatus status);

    @Query("""
    SELECT d
    FROM DeliveryDriver d
    LEFT JOIN d.deliveries del
    GROUP BY d
    ORDER BY COUNT(CASE WHEN del.status = com.example.Food.Delivery.Platform.Backend.Enums.DeliveryStatus.DELIVERED THEN 1 END) DESC
""")
    List<DeliveryDriver> findTopDrivers(Pageable pageable);
}
