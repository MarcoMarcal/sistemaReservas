package com.estudos.reservas.exception;

public class UserRegisterException extends RuntimeException {
    public UserRegisterException(String message) {
        super(message);
    }

    public UserRegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
