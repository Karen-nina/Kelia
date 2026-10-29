package ar.com.kelia.infrastructure.dto;

import lombok.Data;

import java.util.List;
@Data
public class PlaceDetailDTO {
    private String name;
    private String address;
    private String phoneNumber;
    private String website;
    private double rating;
    private int totalUserRatings;

    private double latitude;
    private double longitude;

    private List<String> openingHours;
    private List<PlaceReviewDTO> reviews;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTotalUserRatings() {
        return totalUserRatings;
    }

    public void setTotalUserRatings(int totalUserRatings) {
        this.totalUserRatings = totalUserRatings;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<String> openingHours) {
        this.openingHours = openingHours;
    }

    public List<PlaceReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<PlaceReviewDTO> reviews) {
        this.reviews = reviews;
    }
}