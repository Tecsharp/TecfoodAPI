package com.tecsharp.apis.recipeapp.model;


import com.tecsharp.apis.recipeapp.enums.SlideType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String url;
    private SlideType type;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String b64image;

}

