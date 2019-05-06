package com.milind.chatapp.model;

/**
 * Created by Milind Amrutkar on 5/4/2019.
 */
public class Message {
    String messageID;
    String message;
    User sender;
    String createdAt;

    public Message(String messageID, String message, User sender, String createdAt) {
        this.messageID = messageID;
        this.message = message;
        this.sender = sender;
        this.createdAt = createdAt;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
