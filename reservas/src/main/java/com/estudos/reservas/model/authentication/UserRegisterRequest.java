package com.estudos.reservas.model.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * Lombok não está funcionando corretamente
 */
public class UserRegisterRequest {
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
