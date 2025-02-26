package com.estudos.reservas.controller;

import com.estudos.reservas.enums.UserType;
import com.estudos.reservas.exception.UserRegisterException;
import com.estudos.reservas.model.authentication.UserLoginRequest;
import com.estudos.reservas.model.authentication.UserRegisterRequest;
import com.estudos.reservas.persistence.User;
import com.estudos.reservas.persistence.repository.UserRepository;
import com.estudos.reservas.service.CustomUserDetailsService;
import com.estudos.reservas.util.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthenticationController {
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthenticationController(UserRepository userRepository, CustomUserDetailsService customUserDetailsService, JWTTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
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
    public String generateToken(@RequestBody UserLoginRequest userLoginRequest) {
        var user = userRepository.findByEmail(userLoginRequest.getEmail());

        if (user.isPresent() && isUserValid(userLoginRequest, user.get())) {
         return jwtTokenUtil.generateToken(user.get().getEmail());
        }
        throw new UserRegisterException("Invalid email or password");
    }

    private boolean isUserValid(UserLoginRequest userLoginRequest, User user) {
        return customUserDetailsService.authenticateUser(userLoginRequest.getPassword(), user.getPassword());
    }
}
