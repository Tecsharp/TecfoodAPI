package com.tecsharp.apis.recipeapp.dto;

import com.tecsharp.apis.recipeapp.enums.Difficulty;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
public class RecipeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String description;
    private Integer preparationTime;
    private Difficulty difficulty;
    private String category;
    private Double rating;
}
