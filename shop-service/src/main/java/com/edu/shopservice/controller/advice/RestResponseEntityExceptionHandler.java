package com.edu.shopservice.controller.advice;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.security.sasl.AuthenticationException;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class, EntityExistsException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ProblemDetail entityException(RuntimeException ex, WebRequest request) {
        log.error("Error: ", ex);
        return (ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ProblemDetail entityException(AuthenticationException ex, WebRequest request) {
        log.error("Error: ", ex);
        return (ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

//    @ExceptionHandler(AuthenticationException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    protected ProblemDetail handleAuthException(AuthenticationException ex, WebRequest request) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid token");
//    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ProblemDetail handleAccessException(AccessDeniedException ex, WebRequest request) {
        log.warn("Error: ", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Access denied");
    }


    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
//            HandlerMethodValidationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ProblemDetail validationException(MethodArgumentNotValidException ex, WebRequest request) {
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

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage.toString());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ProblemDetail otherException(Exception ex, WebRequest request) {
        log.error("Error: ", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}
