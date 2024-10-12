package com.tecsharp.apis.recipeapp.controller;

import com.tecsharp.apis.recipeapp.dto.WelcomeMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WelcomeController {

    @GetMapping("/welcome-message")
    public WelcomeMessage getWelcomeMessage() {
        WelcomeMessage welcomeMessage = new WelcomeMessage();
        welcomeMessage.setTitle("¡Bienvenido a TecfoodAPI");
        welcomeMessage.setDescription("Si estás viendo este mensaje, es porque la API de Tecfood está funcionando correctamente y estás logueado.");
        return welcomeMessage;
    }
}