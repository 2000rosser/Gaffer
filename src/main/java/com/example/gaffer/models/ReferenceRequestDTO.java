package com.example.gaffer.models;

import java.util.List;

public class ReferenceRequestDTO {
    
    private String name;
    private String username;
    private String location;
    private String description;
    private List<Reference> references;

    public ReferenceRequestDTO() {}

    public ReferenceRequestDTO(String name, String username, String location, String description, List<Reference> references) {
        this.name = name;
        this.username = username;
        this.location = location;
        this.description = description;
        this.references = references;
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
}
