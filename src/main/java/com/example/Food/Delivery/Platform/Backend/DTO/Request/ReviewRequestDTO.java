package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
import com.example.Food.Delivery.Platform.Backend.Entities.Restaurant;
import com.example.Food.Delivery.Platform.Backend.Entities.Review;
import com.example.Food.Delivery.Platform.Backend.Enums.ReviewTargetType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewRequestDTO {
    @NotNull
    private Customer customer;

    @NotNull
    private Restaurant restaurant;

    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank
    private String comment;

    @Pattern(regexp = "RESTAURANT|DRIVER")
    private ReviewTargetType targetType;

    public Review toEntity() {

        Review review = new Review();

        review.setRating(rating);
        review.setComment(comment);
        review.setCustomer(customer);
        review.setRestaurant(restaurant);
        review.setTargetType(targetType);

        return review;
    }


}
