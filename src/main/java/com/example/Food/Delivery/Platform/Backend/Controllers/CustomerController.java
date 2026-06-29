package com.example.Food.Delivery.Platform.Backend.Controllers;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerAddressRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerAddressResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerPatchDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.OrderResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Enums.OrderStatus;
import com.example.Food.Delivery.Platform.Backend.Services.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
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

  @GetMapping("/{id}/orders/all")
    public List<OrderResponseDTO> getAllCustomerOrders(@PathVariable Integer id){
       return service.getAllCustomerOrders(id);
  }

    @GetMapping("/search")
    public Page<CustomerResponseDTO> searchCustomers(
            @RequestParam(required = false, defaultValue = "") String name,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return service.searchCustomersByName(name, pageable);
    }

    @GetMapping("/{id}/orders")
    public Page<OrderResponseDTO> getCustomerOrders(
            @PathVariable Long id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return service.getCustomerOrders(id, status, from, to, pageable);
    }

    @PatchMapping("/{id}")
    public CustomerResponseDTO patchCustomer(
            @PathVariable Integer id,
            @RequestBody CustomerPatchDTO dto
    ) {
        return service.patchCustomer(id, dto);
    }

}

