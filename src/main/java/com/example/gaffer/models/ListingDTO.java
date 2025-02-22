package com.example.gaffer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ListingDTO {
    @Column
    private String location;

    @Column
    private String minPrice;

    @Column
    private String maxPrice;

    @Column
    private String minBeds;

    @Column
    private String maxBeds;

    @Column
    private String propertyType;

    @Column
    private String furnishing;

    public ListingDTO(){}

    public ListingDTO(String location, String minPrice, String maxPrice, String minBeds, String maxBeds, String propertyType, String furnishing) {
        this.location = location;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minBeds = minBeds;
        this.maxBeds = maxBeds;
        this.propertyType = propertyType;
        this.furnishing = furnishing;
    }



    @Override
    public String toString() {
        return "{" +
            " location='" + getLocation() + "'" +
            ", minPrice='" + getMinPrice() + "'" +
            ", maxPrice='" + getMaxPrice() + "'" +
            ", minBeds='" + getMinBeds() + "'" +
            ", maxBeds='" + getMaxBeds() + "'" +
            ", propertyType='" + getPropertyType() + "'" +
            ", furnishing='" + getFurnishing() + "'" +
            "}";
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinBeds() {
        return this.minBeds;
    }

    public void setMinBeds(String minBeds) {
        this.minBeds = minBeds;
    }

    public String getMaxBeds() {
        return this.maxBeds;
    }

    public void setMaxBeds(String maxBeds) {
        this.maxBeds = maxBeds;
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getFurnishing() {
        return this.furnishing;
    }

    public void setFurnishing(String furnishing) {
        this.furnishing = furnishing;
    }

    public ListingDTO location(String location) {
        setLocation(location);
        return this;
    }

    public ListingDTO minPrice(String minPrice) {
        setMinPrice(minPrice);
        return this;
    }

    public ListingDTO maxPrice(String maxPrice) {
        setMaxPrice(maxPrice);
        return this;
    }

    public ListingDTO minBeds(String minBeds) {
        setMinBeds(minBeds);
        return this;
    }

    public ListingDTO maxBeds(String maxBeds) {
        setMaxBeds(maxBeds);
        return this;
    }

    public ListingDTO propertyType(String propertyType) {
        setPropertyType(propertyType);
        return this;
    }

    public ListingDTO furnishing(String furnishing) {
        setFurnishing(furnishing);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, minPrice, maxPrice, minBeds, maxBeds, propertyType, furnishing);
    }
}
