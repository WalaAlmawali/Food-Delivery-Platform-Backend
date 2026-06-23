package com.example.Food.Delivery.Platform.Backend.Controllers;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Services.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerResponseDTO createCustomer(@RequestBody CustomerRequestDTO dto){
        return service.createCustomer(dto);
    }
}
