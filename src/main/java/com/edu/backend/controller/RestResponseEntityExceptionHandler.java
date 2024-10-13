package com.edu.backend.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class, EntityExistsException.class})
    protected ResponseEntity<Object> entityException(RuntimeException ex, WebRequest request) {
        log.error("Error: ", ex);
        return handleExceptionInternal
                (
                        ex,
                        ex.getMessage(),
                        new HttpHeaders(),
                        HttpStatus.BAD_REQUEST,
                        request
                );
    }

}
