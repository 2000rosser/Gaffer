package com.example.gaffer.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Listing {

    @Id
    private String id;

    @Column
    private String userId;

    @Column
    private String title;

    @Column
    private String seoTitle;

    @Column
    private List<String> sections;

    @Column
    private List<String> saleType;

    @Column
    private LocalDateTime publishDate;

    @Column
    private int price;

    @Column
    private String abbreviatedPrice;

    @Column
    private int numBedrooms;

    @Column
    private int numBathrooms;

    @Column
    private String propertyType;

    @Column(length = 1024)
    private List<String> images;

    @Column
    private String xPoint;

    @Column
    private String yPoint;

    @Column
    private String seoFriendlyPath;

    @Column
    private String category;

    @Column
    private String state;
    // private String daftShortcode;
    // private Seller seller;
    // private String dateOfConstruction;
    // private Media media;
    // private Ber ber;
    // private String platform;
    // private Point point;
    // private String featuredLevel;
    // private boolean premierPartner;
    // private boolean savedAd;

    public Listing(){}

    public Listing(String id, String title, String seoTitle, List<String> sections, List<String> saleType,
                   LocalDateTime publishDate, int price, String abbreviatedPrice, int numBedrooms,
                   int numBathrooms, String propertyType, List<String> images,
                   String xPoint, String yPoint, String seoFriendlyPath, String category, String state) {
        this.id = id;
        this.title = title;
        this.seoTitle = seoTitle;
        this.sections = sections;
        this.saleType = saleType;
        this.publishDate = publishDate;
        this.price = price;
        this.abbreviatedPrice = abbreviatedPrice;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.propertyType = propertyType;
        this.images = images;
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        this.seoFriendlyPath = seoFriendlyPath;
        this.category = category;
        this.state = state;
    }
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeoTitle() {
        return this.seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public List<String> getSections() {
        return this.sections;
    }

    public void setSections(List<String> sections) {
        this.sections = sections;
    }

    public List<String> getSaleType() {
        return this.saleType;
    }

    public void setSaleType(List<String> saleType) {
        this.saleType = saleType;
    }

    public LocalDateTime getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAbbreviatedPrice() {
        return this.abbreviatedPrice;
    }

    public void setAbbreviatedPrice(String abbreviatedPrice) {
        this.abbreviatedPrice = abbreviatedPrice;
    }

    public int getNumBedrooms() {
        return this.numBedrooms;
    }

    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public int getNumBathrooms() {
        return this.numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getXPoint() {
        return this.xPoint;
    }

    public void setXPoint(String xPoint) {
        this.xPoint = xPoint;
    }

    public String getYPoint() {
        return this.yPoint;
    }

    public void setYPoint(String yPoint) {
        this.yPoint = yPoint;
    }

    public String getSeoFriendlyPath() {
        return this.seoFriendlyPath;
    }

    public void setSeoFriendlyPath(String seoFriendlyPath) {
        this.seoFriendlyPath = seoFriendlyPath;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
