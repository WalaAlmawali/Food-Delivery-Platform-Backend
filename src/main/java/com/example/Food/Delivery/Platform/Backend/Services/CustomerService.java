package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
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

    public  CustomerResponseDTO createCustomer(CustomerRequestDTO dto){
        Customer customer = CustomerRequestDTO.toEntity(dto);
        customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(customer);
    }



}
