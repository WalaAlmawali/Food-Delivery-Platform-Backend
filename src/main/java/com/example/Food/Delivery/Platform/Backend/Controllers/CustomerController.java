package com.example.Food.Delivery.Platform.Backend.Controllers;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerAddressRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Services.CustomerService;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/Customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/new_customer")
    public CustomerResponseDTO createCustomer(@RequestBody CustomerRequestDTO dto){
        return service.createCustomer(dto);
    }

    @PostMapping("/customer_address")


    public CustomerResponseDTO createCustomer(@RequestBody CustomerRequestDTO dto ,@RequestBody CustomerAddressRequestDTO initialAddress){
        return service.createCustomer(dto,initialAddress);
    }

}

