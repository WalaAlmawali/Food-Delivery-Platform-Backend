package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.CorporateOrderRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.OrderItemRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CorporateOrderResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.OrderResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.*;
import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import com.example.Food.Delivery.Platform.Backend.Exceptions.InvalidOrderStateException;
import com.example.Food.Delivery.Platform.Backend.Exceptions.InvalidRequestException;
import com.example.Food.Delivery.Platform.Backend.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Backend.Repositories.*;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final CorporateOrderRepository corporateOrderRepository;


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
        orderRepository.save(order);

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
        double total = 0;

        for (OrderItemRequestDTO dto : items) {

            MenuItem menuItem = menuItemRepository.findById(dto.getItem().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item does not exist"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setUnitPrice(menuItem.getPrice());
            orderItem.setTotalPrice(menuItem.getPrice() * dto.getQuantity());

            total += orderItem.getTotalPrice();
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);
        orderRepository.save(order);

        return OrderResponseDTO.fromEntity(order);
    }

    public OrderResponseDTO addItemToOrder(Integer orderId, OrderItemRequestDTO dto) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));

        MenuItem menuItem = menuItemRepository.findById(dto.getItem().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not exist"));

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setMenuItem(menuItem);
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(menuItem.getPrice());
        item.setTotalPrice(menuItem.getPrice() * dto.getQuantity());

        order.getOrderItems().add(item);
        orderRepository.save(order);

        return OrderResponseDTO.fromEntity(order);
    }

    public OrderResponseDTO addMenuItemToOrder(Integer orderId, Integer menuItemId, int quantity) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));

        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not exist"));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setMenuItem(item);
        orderItem.setQuantity(quantity);
        orderItem.setUnitPrice(item.getPrice());
        orderItem.setTotalPrice(item.getPrice() * quantity);
        orderItemRepository.save(orderItem);

        order.getOrderItems().add(orderItem);
        order.setTotalAmount(order.getTotalAmount() + orderItem.getTotalPrice());

        orderRepository.save(order);

        return OrderResponseDTO.fromEntity(order);

    }

    public String removeMenuItemFromOrder(Integer orderId, Integer orderItemId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("order item not exist"));


        if (!order.getOrderItems().contains(orderItem)) {
            return "Order does not contain this item";
        }
        order.getOrderItems().remove(orderItem);

        order.setTotalAmount(order.getTotalAmount() - orderItem.getTotalPrice());
        orderItemRepository.delete(orderItem);

        orderRepository.save(order);


        return "Order Item deleted successfully";

    }

    public OrderResponseDTO applyDiscount(Integer orderId, double discountAmount) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));

        double discount = order.getTotalAmount() * (discountAmount / 100);
        order.setTotalAmount(order.getTotalAmount() - discount);

        orderRepository.save(order);


        return OrderResponseDTO.fromEntity(order);
    }

    public OrderResponseDTO updateOrderStatus(Integer orderId, String newStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist"));

        try {
            OrderStatus status = OrderStatus.valueOf(newStatus);

            order.setStatus(status);
            orderRepository.save(order);

            return OrderResponseDTO.fromEntity(order);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + newStatus);
        }
    }

    public OrderResponseDTO cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStateException(
                    "Only orders with PENDING status can be cancelled.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return OrderResponseDTO.fromEntity(order);
    }

    public OrderResponseDTO calculateOrderTotals(Integer orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));

        double total = 0.0;

        for (OrderItem item : order.getOrderItems()) {
            total += item.getTotalPrice();
        }

        order.setTotalAmount(total);

        orderRepository.save(order);

        return OrderResponseDTO.fromEntity(order);
    }

    public CorporateOrderResponseDTO placeCorporateOrder(CorporateOrderRequestDTO dto) {

        CorporateOrder order = new CorporateOrder();
        order.setCompanyName(dto.getCompanyName());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        List<CorporateOrderItem> items = new ArrayList<>();
        double total = 0.0;

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {

            MenuItem menuItem = menuItemRepository.findById(itemDTO.getItem().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not exist"));

            CorporateOrderItem item = new CorporateOrderItem();
            item.setCorporateOrder(order);
            item.setMenuItem(menuItem);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(menuItem.getPrice());

            double itemTotal = menuItem.getPrice() * itemDTO.getQuantity();
            item.setTotalPrice(itemTotal);

            total += itemTotal;
            items.add(item);
        }

        order.setCorporateOrderItems(items);
        order.setTotalAmount(total);

        corporateOrderRepository.save(order);

        return CorporateOrderResponseDTO.fromEntity(order);
    }

    public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant noe xxist"));


        if (!(restaurant.getAcceptingOrders())) {
            throw new InvalidRequestException("Restaurant is currently not accepting orders.");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setIsActive(true);
        order.setDeliveryFee(restaurant.getDeliveryFee());
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        return OrderResponseDTO.fromEntity(order);
    }

    public OrderResponseDTO confirmOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        return OrderResponseDTO.fromEntity(order);
    }

    public OrderResponseDTO getOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order noe exist"));
        return OrderResponseDTO.fromEntity(order);
    }

    public List<OrderResponseDTO> findByRestaurantIdAndStatus(Integer restaurantId, OrderStatus status) {
        List<Order> orderList = orderRepository.findByRestaurantIdAndStatus(restaurantId, status);
        List<OrderResponseDTO> dtoList = new ArrayList<>();

        for (Order order : orderList) {
            dtoList.add(OrderResponseDTO.fromEntity(order));
        }
        return dtoList;
    }


    @Transactional
    public OrderResponseDTO reorder(Integer orderId) {

        Order oldOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Order newOrder = new Order();
        newOrder.setCustomer(oldOrder.getCustomer());
        newOrder.setRestaurant(oldOrder.getRestaurant());
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setOrderDate(LocalDateTime.now());

        List<OrderItem> items = oldOrder.getOrderItems().stream()
                .map(i -> {
                    OrderItem item = new OrderItem();
                    item.setMenuItem(i.getMenuItem());
                    item.setQuantity(i.getQuantity());
                    item.setUnitPrice(i.getUnitPrice());
                    item.setOrder(newOrder);
                    return item;
                }).toList();

        newOrder.setOrderItems(items);
        orderRepository.save(newOrder);

        return OrderResponseDTO.fromEntity(newOrder);
    }


}
