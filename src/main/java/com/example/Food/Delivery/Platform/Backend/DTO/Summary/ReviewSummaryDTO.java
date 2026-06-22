package com.example.Food.Delivery.Platform.Backend.DTO.Summary;

import com.example.Food.Delivery.Platform.Backend.Entities.Review;

public class ReviewSummaryDTO {
    private Integer id;
    private Integer rating;

    public static ReviewSummaryDTO fromEntity(Review review) {

        ReviewSummaryDTO dto = new ReviewSummaryDTO();

        dto.id=review.getId();
        dto.rating=review.getRating();

        return dto;
    }
}
