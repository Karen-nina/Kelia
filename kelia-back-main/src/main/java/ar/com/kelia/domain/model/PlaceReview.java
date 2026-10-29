package ar.com.kelia.domain.model;

import lombok.Data;

@Data
public class PlaceReview {
    private String authorName;
    private int rating;
    private String text;
    private String relativeTimeDescription;
}
