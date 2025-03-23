package com.edu.shopservice.controller.auth;


import com.edu.shopservice.dto.ClientDto;
import com.edu.shopservice.dto.auth.RegisterDto;
import com.edu.shopservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "AuthController",
        description = "Контроллер для авторизации"
)
@RestController()
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/v1/authorization",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthController {
    private final AuthService authService;

    @PostMapping(
            value = "/register",
            consumes = "application/json"
    )
    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрация пользователя"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDto registerDto) {
        String token = authService.register(registerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }

}

/*
регистрации (/register),
авторизации(/auth),
восстановления пароля(/reset)
 */