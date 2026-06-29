package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Response.DeliveryResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.Delivery;
import com.example.Food.Delivery.Platform.Backend.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Backend.Entities.Order;
import com.example.Food.Delivery.Platform.Backend.Enums.DeliveryStatus;
import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import com.example.Food.Delivery.Platform.Backend.Exceptions.InvalidRequestException;
import com.example.Food.Delivery.Platform.Backend.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Backend.Repositories.DeliveryDriverRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.DeliveryRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.OrderRepository;
import com.example.Food.Delivery.Platform.Backend.Utils.HelperUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final DeliveryDriverRepository deliveryDriverRepository;

    public DeliveryResponseDTO assignDriverToOrder(Integer orderId, Integer driverId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));

        DeliveryDriver driver = deliveryDriverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        if (Boolean.FALSE.equals(driver.getIsOnline())) {
            throw new InvalidRequestException("Driver is not Online");
        }

        if (order.getDelivery() != null) {
            throw new InvalidRequestException("Order already has a delivery assigned");
        }

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDeliveryDriver(driver);
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        delivery.setIsActive(true);
        delivery.setTrackingCode(HelperUtils.generateCode("driver-", 6));

        order.setDelivery(delivery);

        if (driver.getDeliveries() == null) {
            driver.setDeliveries(new ArrayList<>());
        }
        driver.getDeliveries().add(delivery);

        orderRepository.save(order);

        return DeliveryResponseDTO.fromEntity(delivery);
    }

    @Transactional
    public DeliveryResponseDTO autoAssignDriver(Integer orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getDelivery() != null) {
            throw new InvalidRequestException("Order already has a delivery assigned");
        }

        DeliveryDriver driver = deliveryDriverRepository.findFirstAvailableDriver(DeliveryStatus.ASSIGNED)
                .orElseThrow(() -> new InvalidRequestException("No available drivers right now"));

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDeliveryDriver(driver);
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        delivery.setIsActive(true);
        delivery.setTrackingCode(HelperUtils.generateCode("auto-", 6));

        order.setDelivery(delivery);

        if (driver.getDeliveries() == null) {
            driver.setDeliveries(new ArrayList<>());
        }
        driver.getDeliveries().add(delivery);

        orderRepository.save(order);

        return DeliveryResponseDTO.fromEntity(delivery);
    }

    @Transactional
    public void updateDriverLocation(Integer driverId, double lat, double lng) {

        DeliveryDriver driver = deliveryDriverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        if (lat < -90 || lat > 90 || lng < -180 || lng > 180) {
            throw new InvalidRequestException("Invalid coordinates");
        }

        driver.setCurrentLat(lat);
        driver.setCurrentLng(lng);
        driver.setUpdatedDate(LocalDateTime.now());

        deliveryDriverRepository.save(driver);
    }

    @Transactional
    public DeliveryResponseDTO markDeliveryPickedUp(Integer deliveryId) {

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        if (delivery.getStatus() == DeliveryStatus.PICKED_UP) {
            return DeliveryResponseDTO.fromEntity(delivery);
        }

        if (delivery.getStatus() != DeliveryStatus.ASSIGNED) {
            throw new InvalidRequestException(
                    "Delivery cannot be picked up in current status: " + delivery.getStatus()
            );
        }

        delivery.setStatus(DeliveryStatus.PICKED_UP);
        delivery.setPickedUpAt(LocalDateTime.now());
        delivery.setIsActive(true);

        deliveryRepository.save(delivery);

        return DeliveryResponseDTO.fromEntity(delivery);
    }

    @Transactional
    public DeliveryResponseDTO markDeliveryDelivered(Integer deliveryId) {

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

        if (delivery.getStatus() == DeliveryStatus.DELIVERED) {
            return DeliveryResponseDTO.fromEntity(delivery); // idempotent
        }

        if (delivery.getStatus() != DeliveryStatus.PICKED_UP) {
            throw new InvalidRequestException(
                    "Delivery cannot be marked delivered from status: " + delivery.getStatus()
            );
        }

        delivery.setStatus(DeliveryStatus.DELIVERED);
        delivery.setDeliveredAt(LocalDateTime.now());
        delivery.setIsActive(false);

        Order order = delivery.getOrder();
        order.setStatus(OrderStatus.DELIVERED);

        orderRepository.save(order);
        deliveryRepository.save(delivery);

        return DeliveryResponseDTO.fromEntity(delivery);
    }

    @Transactional
    public List<DeliveryResponseDTO> getDeliveriesForDriver(Integer driverId, String status) {

        DeliveryDriver driver = deliveryDriverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        DeliveryStatus deliveryStatus = null;

        if (status != null && !status.isBlank() && !status.equalsIgnoreCase("ALL")) {
            try {
                deliveryStatus = DeliveryStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidRequestException("Invalid status: " + status);
            }
        }

        List<Delivery> deliveries =
                deliveryRepository.findActiveDeliveryByDriver(driverId, deliveryStatus);

        return deliveries.stream()
                .map(DeliveryResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public void toggleDriverOnlineStatus(Integer driverId, boolean isOnline) {

        DeliveryDriver driver = deliveryDriverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        driver.setIsOnline(isOnline);

        deliveryDriverRepository.save(driver);
    }

    public DeliveryResponseDTO getDeliveryById(Integer id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not exist"));

        return DeliveryResponseDTO.fromEntity(delivery);
    }

    public List<DeliveryResponseDTO> findAllByStatus(String status) {

        DeliveryStatus deliveryStatus;
        try {
            deliveryStatus = DeliveryStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid delivery status: " + status);
        }

        List<Delivery> deliveryList = deliveryRepository.findAllByStatus(deliveryStatus);
        List<DeliveryResponseDTO> dtoList = new ArrayList<>();
        for (Delivery delivery : deliveryList) {
            dtoList.add(DeliveryResponseDTO.fromEntity(delivery));
        }
        return dtoList;
    }
}
