package com.example.gaffer.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ChatRoom {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;

    public ChatRoom() {
    }

    public ChatRoom(String id, String chatId, String senderId, String recipientId) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatId() {
        return this.chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return this.recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public ChatRoom id(String id) {
        setId(id);
        return this;
    }

    public ChatRoom chatId(String chatId) {
        setChatId(chatId);
        return this;
    }

    public ChatRoom senderId(String senderId) {
        setSenderId(senderId);
        return this;
    }

    public ChatRoom recipientId(String recipientId) {
        setRecipientId(recipientId);
        return this;
    }
}
