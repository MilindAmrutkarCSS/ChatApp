package com.milind.chatapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Milind Amrutkar on 5/4/2019.
 */
public class Message extends RealmObject {

    @PrimaryKey
    private String messageID;
    private String message;
    private User sender;
    private String createdAt;

    public Message() {

    }

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
