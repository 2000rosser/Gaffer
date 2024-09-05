package com.example.gaffer.models;

public class Application {

    private String name;
    private boolean tcAccepted;
    private String email;
    private String message;
    private int adId;
    private String phone;

    public Application(String name, boolean tcAccepted, String email, String message, int adId, String phone) {
        this.name = name;
        this.tcAccepted = tcAccepted;
        this.email = email;
        this.message = message;
        this.adId = adId;
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTcAccepted() {
        return this.tcAccepted;
    }

    public boolean getTcAccepted() {
        return this.tcAccepted;
    }

    public void setTcAccepted(boolean tcAccepted) {
        this.tcAccepted = tcAccepted;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAdId() {
        return this.adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }
}
