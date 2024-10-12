package com.tecsharp.apis.recipeapp.dto;

import lombok.Data;

@Data
public class WelcomeMessage{
    private String title;
    private String description;

    public WelcomeMessage() {
    }
    public WelcomeMessage(String title, String description) {
        this.title = title;
        this.description = description;
    }

}