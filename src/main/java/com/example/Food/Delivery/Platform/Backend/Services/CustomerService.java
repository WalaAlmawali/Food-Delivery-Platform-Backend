package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerAddressRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerAddressResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
import com.example.Food.Delivery.Platform.Backend.Entities.CustomerAddress;
import com.example.Food.Delivery.Platform.Backend.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Backend.Repositories.CustomerAddressRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;

    public CustomerService(CustomerRepository repository, CustomerAddressRepository customerAddressRepository) {
        this.customerRepository = repository;
        this.customerAddressRepository = customerAddressRepository;
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

    public String deactivateCustomer(Integer customerId){

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist"));

        customer.setIsActive(false);
        customerRepository.save(customer);

        return "Customer was deleted successfully";

    }

}
