package com.tecsharp.apis.recipeapp.enums;

import lombok.Getter;

@Getter
public enum Difficulty {

    EASY(0),
    MEDIUM(1),
    HARD(2);

    private final int difficult;

    Difficulty(int difficulty) {
        this.difficult = difficulty;
    }

}
