package com.estudos.reservas.service;

import com.estudos.reservas.enums.UserType;
import com.estudos.reservas.exception.UserRegisterException;
import com.estudos.reservas.model.authentication.UserLoginRequest;
import com.estudos.reservas.model.authentication.UserRegisterRequest;
import com.estudos.reservas.persistence.User;
import com.estudos.reservas.persistence.repository.UserRepository;
import com.estudos.reservas.util.JWTTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService service;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  JWTTokenUtil jwtTokenUtil;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  AuthenticationManager authenticationManager;

    @Test
    void registerUser_saveUserInDatabase() {
        var userRequest = new UserRegisterRequest();
        userRequest.setName("Name");
        userRequest.setEmail("example@gmail.com");
        userRequest.setPassword("password");

        doReturn(Optional.empty()).when(userRepository).findByEmail(userRequest.getEmail());

        var user = new User.Builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode((userRequest.getPassword())))
                .role(UserType.CLIENTE)
                .build();
        service.registerUser(userRequest);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void registerUser_throwsException_findEmailInDatabase() {
        var userRequest = new UserRegisterRequest();
        userRequest.setName("Name");
        userRequest.setEmail("example@gmail.com");
        userRequest.setPassword("password");

        var user = new User.Builder()
                .email(userRequest.getEmail())
                .build();

        doReturn(Optional.of(user)).when(userRepository).findByEmail(userRequest.getEmail());

        assertThrows(UserRegisterException.class, () -> service.registerUser(userRequest));
        verify(userRepository, never()).save(any());
    }
    @Test
    void generateToken_returnsAuthResponse() {
        var userRequest = new UserLoginRequest();
        userRequest.setEmail("example@gmail.com");
        userRequest.setPassword("password");

        var user = new User.Builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

        doReturn(Optional.of(user)).when(userRepository).findByEmail(userRequest.getEmail());
        doReturn(true).when(passwordEncoder).matches(userRequest.getPassword(), user.getPassword());

        assertDoesNotThrow(() -> service.generateToken(userRequest));
    }

    @Test
    void generateToken_throwsUsernameNotFoundException_whenUserNotFound() {
        var userRequest = new UserLoginRequest();
        userRequest.setEmail("example@gmail.com");
        userRequest.setPassword("password");

        doReturn(Optional.empty()).when(userRepository).findByEmail(userRequest.getEmail());

        assertThrows(UsernameNotFoundException.class, () -> service.generateToken(userRequest));
    }

    @Test
    void generateToken_throwsBadCredentialsException_whenUserNotValid() {
        var userRequest = new UserLoginRequest();
        userRequest.setEmail("example@gmail.com");
        userRequest.setPassword("password");

        var user = new User.Builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

        doReturn(Optional.of(user)).when(userRepository).findByEmail(userRequest.getEmail());
        doReturn(false).when(passwordEncoder).matches(userRequest.getPassword(), user.getPassword());

        assertThrows(BadCredentialsException.class, () -> service.generateToken(userRequest));
    }
}