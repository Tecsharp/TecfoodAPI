package com.tecsharp.apis.recipeapp.utils;

public class Constants {


    private Constants() {
        throw new IllegalStateException("Utility class");
    }
    /* MESSAGES */
    public static final String USER_REGISTERED_SUCCESS = "Usuario registrado con éxito";
    public static final String USER_REGISTERED_FAILED = "Credenciales inválidas";
    public static final String USER_DELETED_SUCCESS = "Usuario eliminado con éxito";
    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String USER_ALREADY_EXIST = "Este usuario ya existe";
    public static final String USER_UPDATED_SUCCESS = "Usuario actualizado con éxito";
    public static final String ERROR_MESSAGE = "error";
    public static final String RECIPE_CREATED_SUCCESS = "Receta creada con éxito";
    public static final String RECIPE_UPDATED_SUCCESS = "Receta actualizada con éxito";
    public static final String USER_NOT_UPDATED = "Usuario no se acutalizó";
    public static final String USER_FOUND = "User: {} found";

    /* FORMAT MESSAGES */
    public static final String CONTROLLER_METHOD = "CONTROLLER: {} - METHOD: {}";
    public static final String USER_DETAILS_FOUND = "USERDETAIL: {}";

    /* ERRORS */
    public static final String USER_WITH_ID_NOT_FOUND = "User with id: {} not found";
    public static final String USER_WITH_USERNAME_FOUND = "User with username: {} found";
    public static final String GENERAL_ERROR_MESSAGE = "Ocurrió un error inesperado. Por favor, inténtelo más tarde.";
    public static final String NO_USERS_FOUND = "No users found";


}
