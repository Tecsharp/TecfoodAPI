package com.tecsharp.apis.recipeapp.model;


import com.tecsharp.apis.recipeapp.enums.SlideType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = {"b64image"})
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String url;

    @Enumerated(EnumType.ORDINAL)
    private SlideType type;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String b64image;

}

