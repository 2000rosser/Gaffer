package com.example.gaffer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LocationEntity {

    @Id
    private String code;

    private String bigName;

    private String name;

    private String slug;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBigName() {
        return this.bigName;
    }

    public void setBigName(String bigName) {
        this.bigName = bigName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}

