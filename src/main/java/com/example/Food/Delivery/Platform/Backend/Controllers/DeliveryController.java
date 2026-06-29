package com.example.Food.Delivery.Platform.Backend.Controllers;

import com.example.Food.Delivery.Platform.Backend.DTO.Response.DeliveryResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Services.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@AllArgsConstructor
public class DeliveryController {
    private final DeliveryService service;

    @PostMapping("/order/{orderId}/assign-manual/{driverId}")
    public DeliveryResponseDTO assignDriver(@PathVariable Integer orderId, @PathVariable Integer driverId ){
        return service.assignDriverToOrder(orderId,driverId);
    }

    @PostMapping("/order/{orderId}/assign-auto")
    public DeliveryResponseDTO assignDriverAuto(@PathVariable Integer orderId){
        return service.autoAssignDriver(orderId);
    }

    @GetMapping("/{id}")
    public DeliveryResponseDTO getById(@PathVariable Integer id){
        return service.getDeliveryById(id);
    }
    @PutMapping("/{id}/pickup")
    public DeliveryResponseDTO markDeliveryPickedUp(@PathVariable Integer id){
        return service.markDeliveryPickedUp(id);

    }
    @PutMapping("/{id}/complete")
    public DeliveryResponseDTO markDeliveryDelivered(@PathVariable Integer id){
        return service.markDeliveryDelivered(id);
    }
    @GetMapping("/status/{status}")
    public List<DeliveryResponseDTO> findAllByStatus(@PathVariable String status){
        return service.findAllByStatus(status);
    }
}
