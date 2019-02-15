package com.exceptionHandlingStuff;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler {

    @NotNull
    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleException(@NotNull EmployeeNotFoundException exception){

        EmployeeErrorResponse errorResponse = new EmployeeErrorResponse();

        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @NotNull
    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleException(@NotNull Exception exception){

        EmployeeErrorResponse errorResponse = new EmployeeErrorResponse();

        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
