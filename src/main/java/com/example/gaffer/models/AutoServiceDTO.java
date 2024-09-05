package com.example.gaffer.models;

import java.util.List;

public class AutoServiceDTO {
    private List<String> locations;
    private String minPrice;
    private String maxPrice;
    private String minBeds;
    private String maxBeds;
    private String propertyType;
    private String furnishing;

    public AutoServiceDTO(){
    }

    public AutoServiceDTO(List<String> locations, String minPrice, String maxPrice, String minBeds, String maxBeds, String propertyType, String furnishing){
        this.locations=locations;
        this.minPrice=minPrice;
        this.maxPrice=maxPrice;
        this.minBeds=minBeds;
        this.maxBeds=maxBeds;
        this.propertyType=propertyType;
        this.furnishing=furnishing;
    }

    public List<String> getLocations() {
        return this.locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
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
}
