package com.estudos.reservas.exception;

public class TableAlreadyRegisterException extends RuntimeException {
    public TableAlreadyRegisterException(String message) {
        super(message);
    }

    public TableAlreadyRegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
