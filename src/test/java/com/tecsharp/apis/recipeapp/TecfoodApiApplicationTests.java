package com.tecsharp.apis.recipeapp;

import com.tecsharp.apis.recipeapp.controller.UserController;
import com.tecsharp.apis.recipeapp.dto.UserDTO;
import com.tecsharp.apis.recipeapp.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testRegisterUserSuccess() {
        UserDTO userDTO = new UserDTO();
        doNothing().when(userService).registerUser(userDTO);
        ResponseEntity<String> response = userController.registerUser(userDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario registrado con éxito", response.getBody());
    }

}