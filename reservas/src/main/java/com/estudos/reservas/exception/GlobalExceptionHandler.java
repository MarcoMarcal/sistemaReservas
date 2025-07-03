package com.estudos.reservas.exception;

import com.estudos.reservas.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TableNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTableNotFound(TableNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler({TableAlreadyRegisterException.class, UserRegisterException.class})
    public ResponseEntity<ErrorResponse> handleCustomExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(new ErrorResponse(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage()));
    }

}

