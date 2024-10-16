package com.edu.backend.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class, EntityExistsException.class})
    protected ResponseEntity<ProblemDetail> entityException(RuntimeException ex, WebRequest request) {
        log.error("Error: ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage()));
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ProblemDetail> validationException(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Error: ", ex);
        // Получение ошибок валидации
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorMessage.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("; ")
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage.toString()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ProblemDetail> otherException(Exception ex, WebRequest request) {
        log.error("Error: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

}
