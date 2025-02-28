package com.estudos.reservas.service;

import com.estudos.reservas.enums.UserType;
import com.estudos.reservas.exception.UserRegisterException;
import com.estudos.reservas.model.authentication.AuthResponse;
import com.estudos.reservas.model.authentication.UserLoginRequest;
import com.estudos.reservas.model.authentication.UserRegisterRequest;
import com.estudos.reservas.persistence.User;
import com.estudos.reservas.persistence.repository.UserRepository;
import com.estudos.reservas.util.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JWTTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthenticationService(UserRepository userRepository, JWTTokenUtil jwtTokenUtil,
                                 PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void registerUser(UserRegisterRequest userRegisterRequest) {
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

    public AuthResponse generateToken(UserLoginRequest userLoginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword())
        );

        var user = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (isUserValid(userLoginRequest, user)) {
            var token = jwtTokenUtil.generateToken(user.getEmail());
            return new AuthResponse(token, "Bearer", jwtTokenUtil.getExpirationTime());
        }
        throw new BadCredentialsException("Invalid email or password");
    }

    private boolean isUserValid(UserLoginRequest userLoginRequest, User user) {
        return passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword());
    }
}
