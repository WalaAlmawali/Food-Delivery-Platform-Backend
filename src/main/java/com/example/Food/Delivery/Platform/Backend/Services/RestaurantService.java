package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Request.ComboMealRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.MenuItemRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.RestaurantOwnerRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Request.RestaurantRequestDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.ComboMealResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.MenuItemResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.RestaurantOwnerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.RestaurantResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.ComboMeal;
import com.example.Food.Delivery.Platform.Backend.Entities.MenuItem;
import com.example.Food.Delivery.Platform.Backend.Entities.Restaurant;
import com.example.Food.Delivery.Platform.Backend.Entities.RestaurantOwner;
import com.example.Food.Delivery.Platform.Backend.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Backend.Repositories.ComboMealRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.MenuItemRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.RestaurantOwnerRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.RestaurantRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantOwnerRepository ownerRepository;
    private final MenuItemRepository menuItemRepository;
    private final ComboMealRepository comboMealRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantOwnerRepository ownerRepository, MenuItemRepository menuItemRepository, ComboMealRepository comboMealRepository) {
        this.restaurantRepository = restaurantRepository;
        this.ownerRepository = ownerRepository;
        this.menuItemRepository = menuItemRepository;
        this.comboMealRepository = comboMealRepository;
    }

    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto, Integer ownerId) {

        RestaurantOwner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not exist"));

        Restaurant restaurant = RestaurantRequestDTO.toEntity(dto);
        restaurant.setRestaurantOwner(owner);
        restaurantRepository.save(restaurant);

        return RestaurantResponseDTO.fromEntity(restaurant);
    }

    public RestaurantResponseDTO toggleAcceptingOrders(Integer restaurantId, boolean status) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist"));

        restaurant.setAcceptingOrders(status);
        restaurantRepository.save(restaurant);
        return RestaurantResponseDTO.fromEntity(restaurant);

    }

    public RestaurantResponseDTO updateDeliveryFee(Integer restaurantId, double newFee) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist"));

        restaurant.setDeliveryFee(newFee);
        restaurantRepository.save(restaurant);
        return RestaurantResponseDTO.fromEntity(restaurant);

    }

    public List<RestaurantResponseDTO> getRestaurantsByCuisine(String cuisine) {

        List<Restaurant> restaurantList = restaurantRepository.findByCuisineTypeIgnoreCase(cuisine);
        List<RestaurantResponseDTO> responseDTOList = new ArrayList<>();

        for (Restaurant restaurant : restaurantList) {
            responseDTOList.add(RestaurantResponseDTO.fromEntity(restaurant));
        }
        return responseDTOList;
    }

    public List<RestaurantResponseDTO> getRestaurantsUnderDeliveryFee(double maxFee) {
        List<Restaurant> restaurantList = restaurantRepository.findByDeliveryFeeLessThanEqual(maxFee);
        List<RestaurantResponseDTO> responseDTOList = new ArrayList<>();

        for (Restaurant restaurant : restaurantList) {
            responseDTOList.add(RestaurantResponseDTO.fromEntity(restaurant));
        }
        return responseDTOList;
    }

    public List<MenuItemResponseDTO> getMenuForRestaurant(Integer restaurantId) {

        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist"));

        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(restaurantId);
        List<MenuItemResponseDTO> itemResponseDTOList = new ArrayList<>();

        for (MenuItem item : menuItems) {
            itemResponseDTOList.add(MenuItemResponseDTO.fromEntity(item));
        }

        return itemResponseDTOList;
    }

    public List<MenuItemResponseDTO> bulkUpdateMenuItemPrices(Integer restaurantId, double percentageIncrease) {

        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist"));

        List<MenuItem> restaurantMenuItems = menuItemRepository.findByRestaurantId(restaurantId);
        List<MenuItemResponseDTO> itemResponseDTOList = new ArrayList<>();

        for (MenuItem item : restaurantMenuItems) {

            double updatePrice = item.getPrice() * (1 + percentageIncrease / 100);
            item.setPrice(updatePrice);

            itemResponseDTOList.add(MenuItemResponseDTO.fromEntity(item));

        }
        menuItemRepository.saveAll(restaurantMenuItems);
        return itemResponseDTOList;
    }

    public List<RestaurantResponseDTO> getAllRestaurant() {

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        List<RestaurantResponseDTO> responseDTOList = new ArrayList<>();

        for (Restaurant restaurant : restaurantList) {
            responseDTOList.add(RestaurantResponseDTO.fromEntity(restaurant));
        }
        return responseDTOList;
    }

    public RestaurantResponseDTO getRestaurantById(Integer restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist"));

        return RestaurantResponseDTO.fromEntity(restaurant);
    }

    public List<ComboMealResponseDTO> getComboMealForRestaurant(Integer restaurantId) {

        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist"));

        List<ComboMeal> comboMealList = comboMealRepository.findAllByRestaurantId(restaurantId);
        List<ComboMealResponseDTO> comboMealResponseDTOS = new ArrayList<>();

        for (ComboMeal meal:comboMealList ){
            comboMealResponseDTOS.add(ComboMealResponseDTO.fromEntity(meal));
        }
        return comboMealResponseDTOS;
    }

    public MenuItemResponseDTO createNewMenuItem(Integer restaurantId, MenuItemRequestDTO dto){

        Restaurant restaurant =restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist"));

        MenuItem item = MenuItemRequestDTO.toEntity(dto);
        item.setRestaurant(restaurant);
        menuItemRepository.save(item);

        return MenuItemResponseDTO.fromEntity(item);
    }

    public String MarkItemOutOfStock(Integer itemId , boolean status){

        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(()-> new ResourceNotFoundException("Item not exist"));

        item.setIsAvailable(status);
        menuItemRepository.save(item);

        return item.getName() + (status? "is available": "is out of stock");
    }

    public ComboMealResponseDTO createComboMeal(Integer restaurantId , ComboMealRequestDTO dto){

        Restaurant restaurant =restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist"));

        ComboMeal meal = ComboMealRequestDTO.toEntity(dto);
        meal.setRestaurant(restaurant);
        comboMealRepository.save(meal);

        return ComboMealResponseDTO.fromEntity(meal);
    }

    public RestaurantOwnerResponseDTO createOwner(RestaurantOwnerRequestDTO dto){
        RestaurantOwner owner = RestaurantOwnerRequestDTO.toEntity(dto);
         ownerRepository.save(owner);
         return RestaurantOwnerResponseDTO.fromEntity(owner);
    }

}
