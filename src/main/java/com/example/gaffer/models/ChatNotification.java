package com.example.gaffer.models;

public class ChatNotification {
    private String id;
    private String senderId;
    private String senderName;

    public ChatNotification() {
    }

    public ChatNotification(String id, String senderId, String senderName) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public ChatNotification id(String id) {
        setId(id);
        return this;
    }

    public ChatNotification senderId(String senderId) {
        setSenderId(senderId);
        return this;
    }

    public ChatNotification senderName(String senderName) {
        setSenderName(senderName);
        return this;
    }

}
