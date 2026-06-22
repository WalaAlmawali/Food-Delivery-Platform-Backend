package com.example.Food.Delivery.Platform.Backend.Repositories;

import com.example.Food.Delivery.Platform.Backend.Entities.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressRepository  extends JpaRepository<CustomerAddress, Integer> {
    @Query("SELECT a FROM CustomerAddress a WHERE a.city = :city AND a.isActive = true")
    List<CustomerAddress> findByCity(@Param("city") String city);

    @Query("SELECT a FROM CustomerAddress a WHERE a.customer.id = :customerId AND a.isActive = true")
    List<CustomerAddress> findAllByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT a FROM CustomerAddress a WHERE a.customer.id = :customerId AND a.isDefault = true AND a.isActive = true")
    Optional<CustomerAddress> findDefaultAddressByCustomerId(@Param("customerId") Integer customerId);
}
