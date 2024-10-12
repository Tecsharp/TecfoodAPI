package com.tecsharp.apis.recipeapp.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ErrorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

}

