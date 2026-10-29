package ar.com.kelia.infrastructure.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    String recipeId;
    String placeId;
    int rating;
    String uid;
    String content;
}
