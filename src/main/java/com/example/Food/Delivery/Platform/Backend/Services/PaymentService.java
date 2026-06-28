package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Response.PaymentResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.Order;
import com.example.Food.Delivery.Platform.Backend.Entities.Payment;
import com.example.Food.Delivery.Platform.Backend.Enums.PaymentMethod;
import com.example.Food.Delivery.Platform.Backend.Enums.PaymentStatus;
import com.example.Food.Delivery.Platform.Backend.Exceptions.InvalidRequestException;
import com.example.Food.Delivery.Platform.Backend.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Backend.Repositories.OrderRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.PaymentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public PaymentResponseDTO processPayment(Integer orderId, String method) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getPayment() != null &&
                order.getPayment().getStatus() == PaymentStatus.SUCCESS) {
            throw new InvalidRequestException("Order has already been paid");
        }

        PaymentMethod paymentMethod;
        try {
            paymentMethod = PaymentMethod.valueOf(method.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid payment method: " + method);
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(PaymentStatus.SUCCESS); // Simulated payment
        payment.setTransactionRef(UUID.randomUUID().toString());
        payment.setProcessedAt(LocalDateTime.now());

        order.setPayment(payment);
        paymentRepository.save(payment);
        orderRepository.save(order);

        return PaymentResponseDTO.fromEntity(payment);
    }



    @Transactional
    public PaymentResponseDTO refundPayment(Integer paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()-> new ResourceNotFoundException("No payment found"));

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new InvalidRequestException("Only successful payments can be refunded");
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);

        return PaymentResponseDTO.fromEntity(payment);
    }

    public PaymentResponseDTO completePayment(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not exist"));

        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        return PaymentResponseDTO.fromEntity(payment);
    }

    public PaymentResponseDTO findPaymentByOrderId(Integer orderId){

        Payment payment = paymentRepository.findPaymentByOrderId(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("No payment found for this order"));
        return PaymentResponseDTO.fromEntity(payment);
    }

}
