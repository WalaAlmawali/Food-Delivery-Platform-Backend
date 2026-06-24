package com.example.Food.Delivery.Platform.Backend.Controllers;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerAddressRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerAddressResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.OrderResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Services.CustomerService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerResponseDTO createCustomer(@RequestBody CustomerRequestDTO dto){
        return service.createCustomer(dto);
    }

  @GetMapping
    public List<CustomerResponseDTO> getAllCustomers(){
        return service.getAllCustomers();

  }

  @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(@PathVariable Integer id){
        return service.getCustomerById(id);
  }

  @GetMapping("/email/{email}")
    public CustomerResponseDTO findByEmail(@PathVariable String email){
        return service.findByEmail(email);
  }

  @PutMapping("/{id}/deactivate")
    public String deleteCustomer(@PathVariable Integer id){
        return service.deactivateCustomer(id);
  }

  @PutMapping("/{id}/loyalty/add/{points}")
    public CustomerResponseDTO updateLoyaltyPoints(@PathVariable Integer id,@PathVariable Integer points){
        return service.updateLoyaltyPoints(id,points);
  }

  @PutMapping("/{id}/loyalty/deduct/{points}")
    public CustomerResponseDTO applyLoyaltyPenalty(@PathVariable Integer id,@PathVariable Integer points){
        return service.applyLoyaltyPenalty(id,points);
  }

  @PostMapping("/{id}/addresses")
    public CustomerAddressResponseDTO addAddress(@PathVariable Integer id,@RequestBody CustomerAddressRequestDTO address){
        return service.addAddress(id,address);
  }

  @GetMapping("/{id}/addresses")
    public List<CustomerAddressResponseDTO> getAllCustomerAddress(@PathVariable Integer id){
        return service.getAllCustomerAddress(id);
  }

  @DeleteMapping("/addresses/{addressId}")
    public String deleteAddress(@PathVariable Integer addressId){
        return service.deleteAddress(addressId);
  }

  @GetMapping("/{id}/orders")
    public List<OrderResponseDTO> getAllCustomerOrders(@PathVariable Integer id){
       return service.getAllCustomerOrders(id);
  }
}

