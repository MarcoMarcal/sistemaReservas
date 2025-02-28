package com.estudos.reservas.controller;

import com.estudos.reservas.model.authentication.UserLoginRequest;
import com.estudos.reservas.model.authentication.UserRegisterRequest;
import com.estudos.reservas.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        authenticationService.registerUser(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody UserLoginRequest userLoginRequest) {
        var token = authenticationService.generateToken(userLoginRequest);
        return ResponseEntity.ok(token);
    }
}
