package com.tecsharp.apis.recipeapp.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@ToString(exclude = {"b64image"})
@Data
public class SlideDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;

    private String title;

    private String description;

    private String url;

    private String b64image;

}
