package com.milind.chatapp.model;

import java.util.ArrayList;

/**
 * Created by Milind Amrutkar on 5/4/2019.
 */
public class BotQuestions {

    private ArrayList<String> questions;

    public void setQuestions() {
        questions = new ArrayList<>();
        questions.add("Your nationality?");
        questions.add("Current City?");
        questions.add("Are you ready to relocate?");
        questions.add("Please provide your age");
        questions.add("Your gender?");
        questions.add("Your qualification?");
        questions.add("Position you applied for?");

    }

    public ArrayList<String> getQuestions() {
        return questions;
    }
}
