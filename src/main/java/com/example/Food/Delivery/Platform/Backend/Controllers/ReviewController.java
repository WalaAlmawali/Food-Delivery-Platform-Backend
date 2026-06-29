package com.example.Food.Delivery.Platform.Backend.Controllers;

import com.example.Food.Delivery.Platform.Backend.DTO.Response.ReviewResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor

public class ReviewController {
    private final ReviewService service;

    @PostMapping("restaurant/{restaurantId}/customer/{customerId}")
    public ReviewResponseDTO leaveRestaurantReview(@PathVariable Integer customerId, @PathVariable Integer restaurantId, @RequestParam String comment,@RequestParam Integer rating){
        return service.leaveRestaurantReview(restaurantId,customerId,rating,comment);
    }

    @PostMapping("/driver/{driverId}/customer/{customerId}")
    public ReviewResponseDTO leaveDriverReview(@PathVariable Integer customerId,@PathVariable Integer driverId, @RequestParam String comment,@RequestParam Integer rating){
        return service.leaveDriverReview(customerId,driverId,rating,comment);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public List<ReviewResponseDTO> getAllRestaurantReview(@PathVariable Integer restaurantId){
        return service.findReviewsByRestaurantId(restaurantId);
    }

    @GetMapping("/driver/{driverId}")
    public List<ReviewResponseDTO> getAllDriverReview(@PathVariable Integer driverId){
        return service.findReviewsByDriverId(driverId);
    }

    @DeleteMapping("/{reviewId}")
    public String deleteReview(@PathVariable Integer reviewId){
        return service.deleteReview(reviewId);
    }
}
