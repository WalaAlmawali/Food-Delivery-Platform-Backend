package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.OrderItemRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.OrderResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.*;
import com.example.Food.Delivery.Platform.Backend.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Backend.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.MenuItemRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.OrderRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId, List<OrderItemRequestDTO> items) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist"));

        Order order = new Order();

        order.setCustomer(customer);
        order.setRestaurant(restaurant);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDTO dto : items) {

            OrderItem orderItem = OrderItemRequestDTO.toEntity(dto);

            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        return OrderResponseDTO.fromEntity(order);
    }

    public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId, List<OrderItemRequestDTO> items, String notes) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist"));

        Order order = new Order();

        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryNotes(notes);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDTO dto : items) {

            OrderItem orderItem = OrderItemRequestDTO.toEntity(dto);

            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        return OrderResponseDTO.fromEntity(order);

    }

   /* public OrderResponseDTO addMenuItemToOrder(Integer orderId, Integer menuItemId, int quantity) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));

        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not exist"));

        if (item.getIsActive()) {
            order.getOrderItems().add(item)
        }

    }*/
}
