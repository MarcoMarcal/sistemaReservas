package com.estudos.reservas.exception;

public class NotPossibleReserveException extends RuntimeException {
    public NotPossibleReserveException(String message) {
        super(message);
    }

    public NotPossibleReserveException(String message, Throwable cause) {
        super(message, cause);
    }
}
