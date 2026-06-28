package com.example.Food.Delivery.Platform.Backend.Services;

import com.example.Food.Delivery.Platform.Backend.DTO.Response.RestaurantOwnerResponseDTO;
import com.example.Food.Delivery.Platform.Backend.DTO.Response.ReviewResponseDTO;
import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
import com.example.Food.Delivery.Platform.Backend.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Backend.Entities.Restaurant;
import com.example.Food.Delivery.Platform.Backend.Entities.Review;
import com.example.Food.Delivery.Platform.Backend.Enums.ReviewTargetType;
import com.example.Food.Delivery.Platform.Backend.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Backend.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.DeliveryDriverRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.RestaurantRepository;
import com.example.Food.Delivery.Platform.Backend.Repositories.ReviewRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final DeliveryDriverRepository deliveryDriverRepository;

    public ReviewResponseDTO leaveRestaurantReview(Integer customerId, Integer restaurantId, int rating, String comment) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Review review = new Review();
        review.setRestaurant(restaurant);
        review.setCustomer(customer);
        review.setRating(rating);
        review.setComment(comment);
        review.setTargetType(ReviewTargetType.RESTAURANT);

        reviewRepository.save(review);
        return ReviewResponseDTO.fromEntity(review);
    }

    public ReviewResponseDTO leaveDriverReview(Integer customerId, Integer driverId, int rating, String comment) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        DeliveryDriver driver = deliveryDriverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not exist"));

        Review review = new Review();
        review.setTargetType(ReviewTargetType.DRIVER);
        review.setCustomer(customer);
        review.setRating(rating);
        review.setComment(comment);
        return ReviewResponseDTO.fromEntity(review);
    }

    public List<ReviewResponseDTO> findReviewsByRestaurantId(Integer restaurantId){

        List<Review> reviewList = reviewRepository.findReviewsByRestaurantId(restaurantId);
        List<ReviewResponseDTO> dtoList = new ArrayList<>();

        for(Review review : reviewList){
            dtoList.add(ReviewResponseDTO.fromEntity(review));
        }
        return dtoList;
    }

    public List<ReviewResponseDTO> findReviewsByDriverId(Integer driverId){
        List<Review> reviewList = reviewRepository.findReviewsByDriverId(driverId);
        List<ReviewResponseDTO> dtoList = new ArrayList<>();

        for(Review review : reviewList){
            dtoList.add(ReviewResponseDTO.fromEntity(review));
        }
        return dtoList;
    }

    public String deleteReview(Integer reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ResourceNotFoundException("No review found"));

        review.setIsActive(false);
        reviewRepository.save(review);
        return "Review deleted successfully";
    }
}
