package com.example.gaffer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Reference {

    enum Type {
        LANDLORD,
        EMPLOYER
    }

    @Column(nullable = false)
    public Type type;

    @Column(nullable = false)
    public String provideName;

    @Column
    public String providerEmail;

    @Column
    public String providerPhoneNumber;

    @Column(nullable = false)
    public String description;

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getProvideName() {
        return this.provideName;
    }

    public void setProvideName(String provideName) {
        this.provideName = provideName;
    }

    public String getProviderEmail() {
        return this.providerEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public String getProviderPhoneNumber() {
        return this.providerPhoneNumber;
    }

    public void setProviderPhoneNumber(String providerPhoneNumber) {
        this.providerPhoneNumber = providerPhoneNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
