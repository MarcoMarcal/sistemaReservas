package com.estudos.reservas.controller;

import com.estudos.reservas.model.authentication.AuthResponse;
import com.estudos.reservas.model.authentication.UserLoginRequest;
import com.estudos.reservas.model.authentication.UserRegisterRequest;
import com.estudos.reservas.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @InjectMocks
    private AuthenticationController authenticationController;
    @Mock
    private AuthenticationService service;

    @Test
    void registerUser_returnsSuccess() {
        var userRequest = new UserRegisterRequest();
        userRequest.setEmail("example@gmail.com");
        userRequest.setName("name");
        userRequest.setPassword("password");
        doNothing().when(service).registerUser(userRequest);
        var result = authenticationController.registerUser(userRequest);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED, result.getStatusCode()),
                () -> assertEquals("User registered successfully", result.getBody()),
                () -> assertDoesNotThrow(() -> result)
        );
    }

    @Test
    void generateToken_returnsSuccess() {
        var userRequest = new UserLoginRequest();
        userRequest.setEmail("example@gmail.com");
        userRequest.setPassword("password");
        var userResponse = new AuthResponse("token", "type", 84000L);
        doReturn(userResponse).when(service).generateToken(userRequest);
        var result = authenticationController.generateToken(userRequest);

        assertAll(
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertDoesNotThrow(() -> result)
        );
    }
}