package com.example.gaffer.models;

import java.util.List;

public class RegisterRequestDTO {
    
    private String name;
    private String username;
    private String password;
    private String location;
    private String description;
    private List<Reference> references;

    public RegisterRequestDTO() {}

    public RegisterRequestDTO(String name, String username, String password, String location, String description, List<Reference> references) {
        this.name = name;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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