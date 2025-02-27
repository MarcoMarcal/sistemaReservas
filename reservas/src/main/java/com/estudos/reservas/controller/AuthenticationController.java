package com.estudos.reservas.controller;

import com.estudos.reservas.enums.UserType;
import com.estudos.reservas.exception.UserRegisterException;
import com.estudos.reservas.model.authentication.AuthResponse;
import com.estudos.reservas.model.authentication.UserLoginRequest;
import com.estudos.reservas.model.authentication.UserRegisterRequest;
import com.estudos.reservas.persistence.User;
import com.estudos.reservas.persistence.repository.UserRepository;
import com.estudos.reservas.service.CustomUserDetailsService;
import com.estudos.reservas.util.JWTTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
public class AuthenticationController {
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthenticationController(UserRepository userRepository, CustomUserDetailsService customUserDetailsService,
                                    JWTTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        userRepository.findByEmail(userRegisterRequest.getEmail())
                .ifPresent(user -> {
                    throw new UserRegisterException("That email is already registered");
                });
            var user = new User.Builder()
                    .name(userRegisterRequest.getName())
                    .email(userRegisterRequest.getEmail())
                    .password(passwordEncoder.encode((userRegisterRequest.getPassword())))
                    .role(UserType.CLIENTE)
                    .build();

            userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody UserLoginRequest userLoginRequest) {
        try {
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword())
            );

            var user = userRepository.findByEmail(userLoginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (isUserValid(userLoginRequest, user)) {
                 var token = jwtTokenUtil.generateToken(user.getEmail());
                 return ResponseEntity.ok(new AuthResponse(token, "Bearer", jwtTokenUtil.getExpirationTime()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not valid");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    private boolean isUserValid(UserLoginRequest userLoginRequest, User user) {
        return customUserDetailsService.authenticateUser(userLoginRequest.getPassword(), user.getPassword());
    }
}
