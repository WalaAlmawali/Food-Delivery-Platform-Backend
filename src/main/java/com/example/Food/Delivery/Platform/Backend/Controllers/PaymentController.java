package com.example.Food.Delivery.Platform.Backend.Controllers;

import com.example.Food.Delivery.Platform.Backend.DTO.Response.PaymentResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Enums.PaymentMethod;
import com.example.Food.Delivery.Platform.Backend.Services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/order/{orderId}")
    public PaymentResponseDTO createPayment(@PathVariable Integer orderId, @RequestParam String method){
        return service.processPayment(orderId,method);
    }

    @PutMapping("/{paymentId}/complete")
    public PaymentResponseDTO completePayment(@PathVariable Integer paymentId){
        return service.completePayment(paymentId);
    }

    @PutMapping("/{paymentId}/refund")
    public PaymentResponseDTO refundPayment(@PathVariable Integer paymentId){
        return service.refundPayment(paymentId);
    }

    @GetMapping("/order/{orderId}")
    public PaymentResponseDTO getPaymentInfoForOrder(@PathVariable Integer orderId){
        return service.findPaymentByOrderId(orderId);
    }

}
