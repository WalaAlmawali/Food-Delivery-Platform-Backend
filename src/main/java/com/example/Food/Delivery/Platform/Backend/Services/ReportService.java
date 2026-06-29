package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.DriverResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Summary.PlatformSummaryDTO;
import com.example.Food.Delivery.Platform.Backend.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.DeliveryDriverRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryDriverRepository driverRepository;

    public BigDecimal getRestaurantRevenue(Integer restaurantId, LocalDate date) {
        return orderRepository.getRestaurantRevenue(restaurantId, date);
    }

    public long getRestaurantOrderCount(Integer restaurantId) {
        return orderRepository.countOrdersByRestaurant(restaurantId);
    }

    public List<CustomerResponseDTO> getTopCustomers() {
        return customerRepository.findTopCustomers(PageRequest.of(0, 10))
                .stream()
                .map(CustomerResponseDTO::fromEntity)
                .toList();
    }


    public List<DriverResponseDTO> getDriverLeaderboard() {
        return driverRepository.findTopDrivers(PageRequest.of(0, 10))
                .stream()
                .map(DriverResponseDTO::fromEntity)
                .toList();
    }


    public PlatformSummaryDTO getDailySummary(LocalDate date) {

        long orders = orderRepository.countOrdersByDate(date);
        BigDecimal deliveryFees = orderRepository.sumDeliveryFeesByDate(date);

        return new PlatformSummaryDTO(orders, deliveryFees);

    }
}

