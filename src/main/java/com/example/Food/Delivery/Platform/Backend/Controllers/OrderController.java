package com.example.Food.Delivery.Platform.Backend.Controllers;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.CorporateOrderRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.OrderItemRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CorporateOrderResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.OrderResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Summary.OrderSummaryDTO;
import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import com.example.Food.Delivery.Platform.Backend.Services.OrderService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/customer/{customerId}/restaurant/{restaurantId}")
    public OrderResponseDTO createOrder(@PathVariable Integer customerId, @PathVariable Integer restaurantId){
        return service.createOrder(customerId,restaurantId);
    }

    @PostMapping("/{id}/items")
    public OrderResponseDTO addItem(@PathVariable Integer id , @RequestBody OrderItemRequestDTO dto){
        return service.addItemToOrder(id,dto);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public String removeMenuItemFromOrder(@PathVariable Integer id, @PathVariable Integer itemId){
        return service.removeMenuItemFromOrder(id, itemId);
    }

    @PutMapping("/{id}/discount/{amount}")
    public OrderResponseDTO applyDiscount(@PathVariable  Integer id, @PathVariable Integer amount){
        return service.applyDiscount(id,amount);
    }

    @PutMapping("/{id}/confirm")
    public OrderResponseDTO confirmOrder(@PathVariable Integer id){
        return service.confirmOrder(id);
    }

    @PutMapping("/{id}/status/{status}")
    public OrderResponseDTO updateStatus(@PathVariable Integer id,@PathVariable String status){
        return service.updateOrderStatus(id,status);
    }

    @PutMapping("/{id}/cancel")
    public OrderResponseDTO cancelOrder(@PathVariable Integer id){
        return service.cancelOrder(id);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrder(@PathVariable Integer id){
        return service.getOrder(id);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<OrderResponseDTO> findByRestaurantIdAndStatus(@PathVariable Integer restaurantId , @RequestParam OrderStatus status){
        return service.findByRestaurantIdAndStatus(restaurantId,status);
    }

    @PostMapping("/corporate")
    public CorporateOrderResponseDTO placeCorporateOrder(@RequestBody CorporateOrderRequestDTO dto){
        return service.placeCorporateOrder(dto);
    }

    @PostMapping("/{id}/reorder")
    public OrderResponseDTO reorder(@PathVariable Integer id) {
        return service.reorder(id);
    }


}