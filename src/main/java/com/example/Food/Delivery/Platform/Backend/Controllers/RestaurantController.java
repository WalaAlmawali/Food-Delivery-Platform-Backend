package com.example.Food.Delivery.Platform.Backend.Controllers;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.ComboMealRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.RestaurantOwnerRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.RestaurantRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.ComboMealResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.MenuItemResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.RestaurantOwnerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.RestaurantResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Summary.RestaurantAnalyticsDTO;
import com.example.Food.Delivery.Platform.Backend.Services.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping("/owner/{ownerId}")
    public RestaurantResponseDTO createRestaurant( @RequestBody RestaurantRequestDTO dto,@PathVariable Integer ownerId){
        return service.createRestaurant(dto,ownerId);
    }

    @GetMapping
    public List<RestaurantResponseDTO> getAllRestaurant(){
        return service.getAllRestaurant();
    }

    @GetMapping("/{id}")
    public RestaurantResponseDTO getRestaurantById(@PathVariable Integer id){
       return service.getRestaurantById(id);
    }

    @GetMapping("/cuisine/{cuisine}")
    public List<RestaurantResponseDTO> getByCuisine(@PathVariable String cuisine){
        return service.getRestaurantsByCuisine(cuisine);
    }

    @PutMapping("/{id}/fee/{newFee}")
    public RestaurantResponseDTO updateDeliveryFee(@PathVariable Integer id,@PathVariable double newFee){
        return service.updateDeliveryFee(id,newFee);
    }

    @PutMapping("/{id}/toggle-orders")
    public RestaurantResponseDTO toggleAcceptingOrders(@PathVariable Integer id,@RequestParam boolean acceptingOrders){
        return service.toggleAcceptingOrders(id,acceptingOrders);
    }

    @GetMapping("/{id}/menu")
    public List<MenuItemResponseDTO> getRestaurantMenu(@PathVariable Integer id){
       return service.getMenuForRestaurant(id);
    }

    @GetMapping("{id}/combos")
    public List<ComboMealResponseDTO> getComboMealForRestaurant(@PathVariable Integer id){
        return service.getComboMealForRestaurant(id);
    }

    @PutMapping("/menu/{itemId}/available")
    public String MarkItemOutOfStock(@PathVariable Integer itemId, @RequestParam boolean status ){
        return service.MarkItemOutOfStock(itemId,status);
    }

    @PostMapping("/{id}/combos")
    public ComboMealResponseDTO createComboMeal(@PathVariable Integer id, @RequestBody ComboMealRequestDTO dto){
        return service.createComboMeal(id,dto);
    }

    @PutMapping("/{id}/bulk-price-increase")
    public List<MenuItemResponseDTO> bulkUpdateMenuItemPrices(@PathVariable Integer id,@RequestParam Integer percentage){
        return service.bulkUpdateMenuItemPrices(id,percentage);
    }
   @PostMapping("/create_owner")
    public RestaurantOwnerResponseDTO createOwner(@RequestBody RestaurantOwnerRequestDTO dto){
        return service.newOwner(dto);
    }

    @GetMapping("/near")
    public List<RestaurantResponseDTO> getNearbyRestaurants(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam double radiusKm
    ) {
        return service.getNearbyRestaurants(lat, lng, radiusKm);
    }

    @GetMapping("/{id}/analytics")
    public RestaurantAnalyticsDTO getAnalytics(@PathVariable Integer id) {
        return service.getAnalytics(id);
    }

    @GetMapping("/{id}/menu/top-sellers")
    public List<MenuItemResponseDTO> getTopSellingItems(@PathVariable Long id) {
        return service.getTopSellingItems(id);
    }

    @GetMapping("/menu/search")
    public List<MenuItemResponseDTO> searchMenuItems(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minCalories,
            @RequestParam(required = false) Integer maxCalories
    ) {
        return service.searchMenuItems(keyword, minCalories, maxCalories);
    }
}
