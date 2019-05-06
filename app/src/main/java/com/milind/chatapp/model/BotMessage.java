package com.milind.chatapp.model;

import java.util.ArrayList;

/**
 * Created by Milind Amrutkar on 5/4/2019.
 */
public class BotMessage {

    private ArrayList<String> questions;

    void setQuestions() {
        questions.add("Hello there. Please provide your name?");
        questions.add("Your city?");
        questions.add("Your age?");
        questions.add("Your gender?");
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }
}
