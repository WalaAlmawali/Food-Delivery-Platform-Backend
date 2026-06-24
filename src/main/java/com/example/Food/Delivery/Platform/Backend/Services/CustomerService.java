package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerAddressRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerAddressResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.OrderResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
import com.example.Food.Delivery.Platform.Backend.Entities.CustomerAddress;
import com.example.Food.Delivery.Platform.Backend.Entities.Order;
import com.example.Food.Delivery.Platform.Backend.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Backend.Repositories.CustomerAddressRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, CustomerAddressRepository customerAddressRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.orderRepository = orderRepository;
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {
        Customer customer = CustomerRequestDTO.toEntity(dto);
        customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(customer);
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto, CustomerAddressRequestDTO initialAddress) {
        Customer customer = CustomerRequestDTO.toEntity(dto);
        customerRepository.save(customer);

        CustomerAddress customerAddress = CustomerAddressRequestDTO.toEntity(initialAddress);
        customerAddress.setCustomer(customer);

        customerAddressRepository.save(customerAddress);

        return CustomerResponseDTO.fromEntity(customer);

    }

    public CustomerAddressResponseDTO addAddress(Integer customerId, CustomerAddressRequestDTO address) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));

        CustomerAddress customerAddress = CustomerAddressRequestDTO.toEntity(address);
        customerAddress.setCustomer(customer);
        customerAddressRepository.save(customerAddress);

        return CustomerAddressResponseDTO.fromEntity(customerAddress);
    }

    public CustomerResponseDTO updateLoyaltyPoints(Integer customerId, int points) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));

        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + points);
        customerRepository.save(customer);

        return CustomerResponseDTO.fromEntity(customer);
    }

    public CustomerResponseDTO applyLoyaltyPenalty(Integer customerId, int pointsDeducted) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));

        customer.setLoyaltyPoints(customer.getLoyaltyPoints() - pointsDeducted);
        customerRepository.save(customer);

        return CustomerResponseDTO.fromEntity(customer);

    }

    public String deactivateCustomer(Integer customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));

        customer.setIsActive(false);
        customerRepository.save(customer);

        return "Customer was deleted successfully";

    }

    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerResponseDTO> customerResponseDTOListList = new ArrayList<>();


        for (Customer customer : customerList) {
            customerResponseDTOListList.add(CustomerResponseDTO.fromEntity(customer));
        }
        return customerResponseDTOListList;
    }

    public CustomerResponseDTO getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));
        return CustomerResponseDTO.fromEntity(customer);
    }

    public CustomerResponseDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));

        return CustomerResponseDTO.fromEntity(customer);
    }

    public List<CustomerAddressResponseDTO> getAllCustomerAddress(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));
        List<CustomerAddress> customerAddresses = customerAddressRepository.findAllByCustomerId(customerId);
        List<CustomerAddressResponseDTO> customerAddressResponseDTOS = new ArrayList<>();

        for(CustomerAddress address: customerAddresses){
            customerAddressResponseDTOS.add(CustomerAddressResponseDTO.fromEntity(address));
        }

        return customerAddressResponseDTOS;
    }

    public String deleteAddress(Integer id){
        CustomerAddress address = customerAddressRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Address not exist"));
        address.setIsActive(false);
        customerAddressRepository.save(address);

        return "Address was deleted successfully";
    }

    public List<OrderResponseDTO> getAllCustomerOrders(Integer customerId){

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));

        List<Order> customerOrdersList = orderRepository.findByCustomerId(customerId);
        List<OrderResponseDTO> customerOrderDTO = new ArrayList<>();

        for(Order order : customerOrdersList){
            customerOrderDTO.add(OrderResponseDTO.fromEntity(order));
        }
            return customerOrderDTO;
    }
}
