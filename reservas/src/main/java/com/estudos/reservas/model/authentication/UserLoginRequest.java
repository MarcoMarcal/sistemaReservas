package com.estudos.reservas.model.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class UserLoginRequest {
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
