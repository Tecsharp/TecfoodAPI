package com.tecsharp.apis.recipeapp.enums;

import lombok.Getter;

@Getter
public enum SlideType {

    INDEX(0),
    RECIPE(1),
    ABOUT(2),
    CONTACT(3);

    private final int slide;

    SlideType(int slide) {
        this.slide = slide;
    }
}
