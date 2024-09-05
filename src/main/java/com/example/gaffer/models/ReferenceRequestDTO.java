package com.example.gaffer.models;

import java.util.List;

public class ReferenceRequestDTO {
    
    private String name;
    private String username;
    private String phoneNumber;
    private String location;
    private String description;
    private List<Reference> references;
    private String occupation;
    private String placeOfWork;

    public ReferenceRequestDTO() {}

    public ReferenceRequestDTO(String name, String username, String phoneNumber, String location, String description, List<Reference> references, String occupation, String placeofWork) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.description = description;
        this.references = references;
        this.occupation = occupation;
        this.placeOfWork = placeofWork;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public List<Reference> getReferences() {
        return this.references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPlaceOfWork() {
        return this.placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }
}
