package com.milind.chatapp.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Milind Amrutkar on 5/8/2019.
 */
public class Chat extends RealmObject {

    @PrimaryKey
    String chatId;
    RealmList<Message> messages;

    public Chat() {
    }

    public Chat(String chatId, RealmList<Message> messages) {
        this.chatId = chatId;
        this.messages = messages;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public RealmList<Message> getMessages() {
        return messages;
    }

    public void setMessages(RealmList messages) {
        this.messages = messages;
    }
}
