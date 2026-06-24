package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerAddressRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
import com.example.Food.Delivery.Platform.Backend.Entities.CustomerAddress;
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

        CustomerAddress address = CustomerAddressRequestDTO.toEntity(initialAddress);
        address.setCustomer(customer);

        customerAddressRepository.save(address);

        return CustomerResponseDTO.fromEntity(customer);

    }

    public void addAddress(Integer customerId, CustomerAddressRequestDTO address){

    }


}
