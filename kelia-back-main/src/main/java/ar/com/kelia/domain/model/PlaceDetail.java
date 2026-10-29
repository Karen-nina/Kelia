package ar.com.kelia.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class PlaceDetail {
    private String name;
    private String address;
    private String phoneNumber;
    private String website;
    private double rating;
    private int totalUserRatings;

    private double latitude;
    private double longitude;

    private List<String> openingHours;
    private List<PlaceReview> reviews;
}
