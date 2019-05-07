package com.milind.chatapp.model;

import io.realm.RealmObject;

/**
 * Created by Milind Amrutkar on 5/4/2019.
 */
public class User extends RealmObject {
    private String userId;
    private String nickname;
    private String profileUrl;

    public User() {

    }

    public User(String userId, String nickname, String profileUrl) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
