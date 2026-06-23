package com.example.Food.Delivery.Platform.Backend.DTO.Request;

import com.example.Food.Delivery.Platform.Backend.Entities.Customer;
import com.example.Food.Delivery.Platform.Backend.Entities.Restaurant;
import com.example.Food.Delivery.Platform.Backend.Entities.Review;
import com.example.Food.Delivery.Platform.Backend.Enums.ReviewTargetType;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public static Review toEntity(ReviewRequestDTO dto) {

        Review review = new Review();

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setCustomer(dto.getCustomer());
        review.setRestaurant(dto.getRestaurant());
        review.setTargetType(dto.getTargetType());

        return review;
    }


}
